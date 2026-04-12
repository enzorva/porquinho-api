package br.com.fiap.porquinho.presentation.transferObjects.FinancialSummary;

import java.math.BigDecimal;

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
public class AccountSummaryDTO {

    private Long accountId;
    private String accountName;
    private BigDecimal balance;
    private int transactionCount;
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
}
