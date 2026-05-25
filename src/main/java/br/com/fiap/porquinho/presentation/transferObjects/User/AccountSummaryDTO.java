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
                .balance(account.getBalance())
                .build();
    }
}
