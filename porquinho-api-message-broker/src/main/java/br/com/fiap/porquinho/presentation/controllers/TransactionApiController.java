package br.com.fiap.porquinho.presentation.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.porquinho.domainmodel.Account;
import br.com.fiap.porquinho.domainmodel.Transaction;
import br.com.fiap.porquinho.infrastructure.clients.AccountFeignClient;
import br.com.fiap.porquinho.infrastructure.messaging.BalanceUpdateMessage;
import br.com.fiap.porquinho.infrastructure.messaging.TransactionMessageProducer;
import br.com.fiap.porquinho.infrastructure.messaging.TransactionNotificationMessage;
import br.com.fiap.porquinho.presentation.transferObjects.Account.AccountDTO;
import br.com.fiap.porquinho.presentation.transferObjects.Transaction.CreateTransactionDTO;
import br.com.fiap.porquinho.presentation.transferObjects.Transaction.TransactionDTO;
import br.com.fiap.porquinho.service.Account.AccountService;
import br.com.fiap.porquinho.service.Transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transactions")
@Tag(name = "Transações", description = "Operações de gerenciamento de transações: cadastro, consulta, atualização, remoção e listagem de transações")
public class TransactionApiController {

    private final TransactionService<Transaction, Long> transactionService;
    private final AccountService<Account, Long> accountService;
    private final AccountFeignClient accountFeignClient;
    private final TransactionMessageProducer messageProducer;

    @Operation(summary = "Listar todas as transações", description = "Retorna uma lista contendo todas as transações cadastradas no sistema.")
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> findAll() {
        return ResponseEntity.ok(transactionService.findAll()
                .stream()
                .map(TransactionDTO::fromEntity)
                .toList());
    }

    @Operation(summary = "Buscar transação por ID", description = "Retorna as informações de uma transação específica com base no seu identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable Long id) {
        return transactionService.findById(id)
                .map(transaction -> ResponseEntity.ok(TransactionDTO.fromEntity(transaction)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar nova transação", description = "Cria um novo registro de transação no sistema. Utiliza Feign client para validar a conta e envia mensagem via RabbitMQ para atualização assíncrona do saldo.")
    @PostMapping
    public ResponseEntity<TransactionDTO> save(@Valid @RequestBody CreateTransactionDTO createTransactionDTO) {

        // Feign client: busca dados da conta para validação síncrona
        AccountDTO accountDTO;
        try {
            accountDTO = accountFeignClient.findById(createTransactionDTO.getAccountId());
        } catch (Exception e) {
            log.error("Erro ao buscar conta via Feign: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Conta não encontrada com ID: " + createTransactionDTO.getAccountId());
        }

        if (accountDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Conta não encontrada com ID: " + createTransactionDTO.getAccountId());
        }

        // Validação: verificar se a conta tem saldo suficiente para despesas
        if (createTransactionDTO.getTransactionValue().signum() < 0) {
            var absValue = createTransactionDTO.getTransactionValue().abs();
            if (accountDTO.getBalance().compareTo(absValue) < 0
                    && (accountDTO.getOverdraft() == null || accountDTO.getOverdraft() == 0)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Saldo insuficiente na conta. Saldo atual: " + accountDTO.getBalance());
            }
        }

        // Buscar a entidade Account para associar à transação
        Account account = accountService.findById(createTransactionDTO.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada."));

        Transaction transaction = CreateTransactionDTO.toEntity(createTransactionDTO);
        transaction.setAccount(account);

        Transaction newTransaction = transactionService.create(transaction);

        // RabbitMQ: enviar mensagem para atualização assíncrona do saldo
        messageProducer.sendBalanceUpdate(BalanceUpdateMessage.builder()
                .accountId(account.getAccountId())
                .transactionValue(newTransaction.getTransactionValue())
                .transactionId(newTransaction.getTransactionId())
                .description(newTransaction.getDescription())
                .build());

        // RabbitMQ: enviar notificação da transação
        messageProducer.sendTransactionNotification(TransactionNotificationMessage.builder()
                .transactionId(newTransaction.getTransactionId())
                .accountId(account.getAccountId())
                .accountName(account.getName())
                .transactionValue(newTransaction.getTransactionValue())
                .description(newTransaction.getDescription())
                .transactionDate(newTransaction.getTransactionDate())
                .status("CRIADA")
                .build());

        return new ResponseEntity<>(TransactionDTO.fromEntity(newTransaction), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar transação existente", description = "Atualiza completamente os dados de uma transação já existente com base no ID informado.")
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> update(@PathVariable Long id,
            @Valid @RequestBody CreateTransactionDTO createTransactionDTO) {
        if (!transactionService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada.");
        }

        Account account = accountService.findById(createTransactionDTO.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada."));

        Transaction transaction = CreateTransactionDTO.toEntity(createTransactionDTO);
        transaction.setTransactionId(id);
        transaction.setAccount(account);

        Transaction updatedTransaction = transactionService.create(transaction);
        return ResponseEntity.ok(TransactionDTO.fromEntity(updatedTransaction));
    }

    @Operation(summary = "Atualizar parcialmente uma transação", description = "Permite atualizar apenas campos específicos de uma transação existente. "
            + "Os campos não informados permanecem inalterados.")
    @PatchMapping("/{id}")
    public ResponseEntity<TransactionDTO> partialUpdate(@PathVariable Long id,
            @RequestBody CreateTransactionDTO createTransactionDTO) {
        try {
            Transaction updatedTransaction = transactionService.partialUpdate(id,
                    CreateTransactionDTO.toEntity(createTransactionDTO));
            return new ResponseEntity<>(TransactionDTO.fromEntity(updatedTransaction), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Remover transação", description = "Exclui permanentemente uma transação do sistema com base no ID informado.")
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody Long id) {
        if (!transactionService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        transactionService.removeById(id);
        return ResponseEntity.noContent().build();
    }
}
