package br.com.fiap.porquinho.presentation.transferObjects.FinancialSummary;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialSummaryDTO {

    private Long userId;
    private int totalWallets;
    private int totalAccounts;
    private int totalTransactions;
    private BigDecimal totalBalance;
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal netBalance;
    private List<WalletSummaryDTO> wallets;
}
