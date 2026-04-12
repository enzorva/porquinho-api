package br.com.fiap.porquinho.presentation.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.porquinho.domainmodel.AccountType;
import br.com.fiap.porquinho.presentation.transferObjects.AccountType.AccountTypeDTO;
import br.com.fiap.porquinho.service.AccountType.AccountTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/account-types")
@Tag(name = "Tipos de Conta", description = "Operações de consulta de tipos de conta.")
public class AccountTypeApiController {

    private final AccountTypeService<AccountType, Long> accountTypeService;

    @Operation(summary = "Listar todos os tipos de conta", description = "Retorna uma lista contendo todos os tipos de conta cadastrados no sistema.")
    @GetMapping
    public ResponseEntity<List<AccountTypeDTO>> findAll() {
        return ResponseEntity.ok(
                accountTypeService.findAll()
                        .stream()
                        .map(AccountTypeDTO::fromEntity)
                        .toList());
    }

    @Operation(summary = "Buscar tipo de conta por ID", description = "Retorna as informações de um tipo de conta específico com base no seu identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<AccountTypeDTO> findById(@PathVariable Long id) {
        return accountTypeService.findById(id)
                .map(type -> ResponseEntity.ok(AccountTypeDTO.fromEntity(type)))
                .orElse(ResponseEntity.notFound().build());
    }

}
