package br.com.fiap.porquinho.presentation.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.porquinho.domainmodel.Bank;
import br.com.fiap.porquinho.presentation.transferObjects.Bank.BankDTO;
import br.com.fiap.porquinho.service.Bank.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/banks")
@Tag(name = "Bancos", description = "Operações de consulta de bancos.")
public class BankApiController {

    private final BankService<Bank, Long> bankService;

    @Operation(summary = "Listar todos os bancos", description = "Retorna uma lista contendo todos os bancos cadastrados no sistema.")
    @GetMapping
    public ResponseEntity<List<BankDTO>> findAll() {
        return ResponseEntity.ok(
                bankService.findAll()
                        .stream()
                        .map(BankDTO::fromEntity)
                        .toList());
    }

    @Operation(summary = "Buscar banco por ID", description = "Retorna as informações de um banco específico com base no seu identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<BankDTO> findById(@PathVariable Long id) {
        return bankService.findById(id)
                .map(bank -> ResponseEntity.ok(BankDTO.fromEntity(bank)))
                .orElse(ResponseEntity.notFound().build());
    }

}
