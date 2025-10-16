package br.com.fiap.porquinho.presentation.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.fiap.porquinho.domainmodel.Bank;
import br.com.fiap.porquinho.presentation.transferObjects.BankDTO;
import br.com.fiap.porquinho.presentation.transferObjects.CreateBankDTO;
import br.com.fiap.porquinho.service.BankService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/banks")
@Tag(
    name = "Bancos",
    description = "Operações de gerenciamento de bancos: cadastro, consulta, atualização, remoção e listagem."
)
public class BankApiController {

    private final BankService<Bank, Long> bankService;

    @Operation(
        summary = "Listar todos os bancos",
        description = "Retorna uma lista contendo todos os bancos cadastrados no sistema."
    )
    @GetMapping
    public ResponseEntity<List<BankDTO>> findAll() {
        return ResponseEntity.ok(
            bankService.findAll()
                .stream()
                .map(BankDTO::fromEntity)
                .toList()
        );
    }

    @Operation(
        summary = "Buscar banco por ID",
        description = "Retorna as informações de um banco específico com base no seu identificador único."
    )
    @GetMapping("/{id}")
    public ResponseEntity<BankDTO> findById(@PathVariable Long id) {
        return bankService.findById(id)
            .map(bank -> ResponseEntity.ok(BankDTO.fromEntity(bank)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Cadastrar novo banco",
        description = "Cria um novo registro de banco no sistema com os dados informados."
    )
    @PostMapping
    public ResponseEntity<BankDTO> save(@Valid @RequestBody CreateBankDTO createBankDTO) {
        Bank newBank = bankService.create(CreateBankDTO.toEntity(createBankDTO));
        return new ResponseEntity<>(BankDTO.fromEntity(newBank), HttpStatus.CREATED);
    }
}
