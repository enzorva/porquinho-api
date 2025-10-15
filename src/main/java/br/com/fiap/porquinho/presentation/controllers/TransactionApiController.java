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

import br.com.fiap.porquinho.domainmodel.Transaction;
import br.com.fiap.porquinho.presentation.transferObjects.CreateTransactionDTO;
import br.com.fiap.porquinho.presentation.transferObjects.TransactionDTO;
import br.com.fiap.porquinho.service.TransactionService;
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

    @Operation(summary = "Cadastrar nova transação", description = "Cria um novo registro de transação no sistema com os dados informados.")
    @PostMapping
    public ResponseEntity<TransactionDTO> save(@Valid @RequestBody CreateTransactionDTO createTransactionDTO) {

        Transaction newTransaction = transactionService.create(
                CreateTransactionDTO.toEntity(createTransactionDTO));

        return new ResponseEntity<>(TransactionDTO.fromEntity(newTransaction), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar transação existente", description = "Atualiza completamente os dados de uma transação já existente com base no ID informado.")
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> update(@PathVariable Long id,
            @Valid @RequestBody CreateTransactionDTO createTransactionDTO) {
        if (!transactionService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada.");
        }

        Transaction transaction = CreateTransactionDTO.toEntity(createTransactionDTO);
        transaction.setTransactionId(id);

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
