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

import br.com.fiap.porquinho.domainmodel.Wallet;
import br.com.fiap.porquinho.domainmodel.Account;
import br.com.fiap.porquinho.presentation.transferObjects.Account.AccountDTO;
import br.com.fiap.porquinho.presentation.transferObjects.Account.CreateAccountDTO;
import br.com.fiap.porquinho.service.Account.AccountService;
import br.com.fiap.porquinho.service.Wallet.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/accounts")
@Tag(name = "Contas", description = "Operações de gerenciamento de contas: cadastro, consulta, atualização, remoção e listagem de carteiras")
public class AccountApiController {

    private final AccountService<Account, Long> accountService;

    private final WalletService<Wallet, Long> walletService;

    @Operation(summary = "Listar todas as contas", description = "Retorna uma lista contendo todas as contas cadastradas no sistema.")
    @GetMapping
    public ResponseEntity<List<AccountDTO>> findAll() {
        return ResponseEntity.ok(accountService.findAll()
                .stream()
                .map(AccountDTO::fromEntity)
                .toList());
    }

    @Operation(summary = "Buscar conta por ID", description = "Retorna as informações de uma conta específica com base no seu identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable Long id) {
        return accountService.findById(id)
                .map(account -> ResponseEntity.ok(AccountDTO.fromEntity(account)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar nova conta", description = "Cria um novo registro de conta no sistema com os dados informados.")
    @PostMapping
    public ResponseEntity<AccountDTO> save(@Valid @RequestBody CreateAccountDTO createAccountDTO) {
        Wallet wallet = walletService.findById(createAccountDTO.getWalletId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Conta não encontrado."));

        Account newAccount = accountService.create(CreateAccountDTO.toEntity(createAccountDTO, wallet));
        return new ResponseEntity<>(AccountDTO.fromEntity(newAccount), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar conta existente", description = "Atualiza completamente os dados de uma conta já existente com base no ID informado.")
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Long id,
            @Valid @RequestBody CreateAccountDTO createAccountDTO) {
        if (!accountService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrado.");
        }

        Wallet wallet = walletService.findById(createAccountDTO.getWalletId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Conta não encontrado."));

        Account account = CreateAccountDTO.toEntity(createAccountDTO, wallet);
        account.setAccountId(id);

        Account updatedAccount = accountService.create(account);
        return ResponseEntity.ok(AccountDTO.fromEntity(updatedAccount));
    }

    @Operation(summary = "Atualizar parcialmente uma conta", description = "Permite atualizar apenas campos específicos de uma conta existente. "
            + "Os campos não informados permanecem inalterados.")
    @PatchMapping("/{id}")
    public ResponseEntity<AccountDTO> partialUpdate(@PathVariable Long id,
            @RequestBody CreateAccountDTO createAccountDTO) {
        try {
            Account account = accountService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrado."));

            Account updatedAccount = accountService.partialUpdate(id,
                    CreateAccountDTO.toEntity(createAccountDTO, account.getWallet()));
            return new ResponseEntity<>(AccountDTO.fromEntity(updatedAccount), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Remover conta", description = "Exclui permanentemente uma conta do sistema com base no ID informado.")
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody Long id) {
        if (!accountService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        accountService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}