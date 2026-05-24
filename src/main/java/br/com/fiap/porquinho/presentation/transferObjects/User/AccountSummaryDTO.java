package br.com.fiap.porquinho.presentation.transferObjects.User;

import java.math.BigDecimal;

import br.com.fiap.porquinho.domainmodel.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AccountSummaryDTO {

    private Long id;

    private String name;

    private String icon;

    private BigDecimal balance;

    public static AccountSummaryDTO fromEntity(Account account) {
        if (account == null)
            return null;

        return AccountSummaryDTO.builder()
                .id(account.getAccountId())
                .name(account.getName())
                .icon(account.getBank() != null && account.getBank().getLogoUrl() != null 
                    ? account.getBank().getLogoUrl() 
                    : "default-icon")
                .balance(account.getBalance())
                .build();
    }
}
