package br.com.fiap.porquinho.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.porquinho.infrastructure.clients.WalletFeignClient;
import br.com.fiap.porquinho.presentation.transferObjects.FinancialSummary.FinancialSummaryDTO;
import br.com.fiap.porquinho.presentation.transferObjects.Wallet.WalletDTO;
import br.com.fiap.porquinho.service.FinancialSummary.FinancialSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/financial-summary")
@Tag(name = "Resumo Financeiro", description = "Operações de geração de resumo financeiro consolidado do usuário")
public class FinancialSummaryApiController {

    private final FinancialSummaryService financialSummaryService;
    private final WalletFeignClient walletFeignClient;

    @Operation(summary = "Gerar resumo financeiro do usuário",
            description = "Gera um resumo financeiro consolidado com totais de saldo, receitas e despesas. "
                    + "Utiliza Feign client para validar as carteiras do usuário antes de gerar o relatório.")
    @GetMapping("/{userId}")
    public ResponseEntity<FinancialSummaryDTO> getSummary(@PathVariable Long userId) {

        // Feign client: validar que o usuário possui carteiras
        List<WalletDTO> wallets;
        try {
            wallets = walletFeignClient.findAll();
        } catch (Exception e) {
            log.error("Erro ao buscar carteiras via Feign: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Não foi possível consultar as carteiras do usuário.");
        }

        boolean hasWallets = wallets.stream()
                .anyMatch(w -> w.getUserId().equals(userId));

        if (!hasWallets) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Nenhuma carteira encontrada para o usuário com ID: " + userId);
        }

        FinancialSummaryDTO summary = financialSummaryService.generateSummary(userId);
        return ResponseEntity.ok(summary);
    }
}
