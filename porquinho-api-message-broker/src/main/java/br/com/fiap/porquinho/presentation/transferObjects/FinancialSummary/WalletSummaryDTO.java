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
public class WalletSummaryDTO {

    private Long walletId;
    private String walletName;
    private int totalAccounts;
    private BigDecimal totalBalance;
    private List<AccountSummaryDTO> accounts;
}
