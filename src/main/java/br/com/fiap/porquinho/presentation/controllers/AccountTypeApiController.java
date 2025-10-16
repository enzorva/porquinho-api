package br.com.fiap.porquinho.presentation.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.fiap.porquinho.domainmodel.AccountType;
import br.com.fiap.porquinho.presentation.transferObjects.AccountTypeDTO;
import br.com.fiap.porquinho.presentation.transferObjects.CreateAccountTypeDTO;
import br.com.fiap.porquinho.service.AccountTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/account-types")
@Tag(
    name = "Tipos de Conta",
    description = "Operações de gerenciamento de tipos de conta: cadastro, consulta, atualização, remoção e listagem."
)
public class AccountTypeApiController {

    private final AccountTypeService<AccountType, Long> accountTypeService;

    @Operation(
        summary = "Listar todos os tipos de conta",
        description = "Retorna uma lista contendo todos os tipos de conta cadastrados no sistema."
    )
    @GetMapping
    public ResponseEntity<List<AccountTypeDTO>> findAll() {
        return ResponseEntity.ok(
            accountTypeService.findAll()
                .stream()
                .map(AccountTypeDTO::fromEntity)
                .toList()
        );
    }

    @Operation(
        summary = "Buscar tipo de conta por ID",
        description = "Retorna as informações de um tipo de conta específico com base no seu identificador único."
    )
    @GetMapping("/{id}")
    public ResponseEntity<AccountTypeDTO> findById(@PathVariable Long id) {
        return accountTypeService.findById(id)
            .map(type -> ResponseEntity.ok(AccountTypeDTO.fromEntity(type)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Cadastrar novo tipo de conta",
        description = "Cria um novo registro de tipo de conta no sistema com os dados informados."
    )
    @PostMapping
    public ResponseEntity<AccountTypeDTO> save(@Valid @RequestBody CreateAccountTypeDTO createAccountTypeDTO) {
        AccountType newType = accountTypeService.create(CreateAccountTypeDTO.toEntity(createAccountTypeDTO));
        return new ResponseEntity<>(AccountTypeDTO.fromEntity(newType), HttpStatus.CREATED);
    }
}

