package br.com.fiap.porquinho.presentation.transferObjects;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// import br.com.fiap.porquinho.domainmodel.User;
import br.com.fiap.porquinho.domainmodel.Wallet;
import br.com.fiap.porquinho.domainmodel.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AccountDTO {

    private Long accountId;

    private Long walletId;

    private String name;

    private BigDecimal balance;

    private Integer overdraft;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static AccountDTO fromEntity(Account account) {
        if (account == null)
            return null;

        return AccountDTO.builder()
                .accountId(account.getAccountId())
                .walletId(account.getWallet().getWalletId())
                .name(account.getName())
                .balance(account.getBalance())
                .overdraft(account.getOverdraft())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
    }

    public static Account toEntity(AccountDTO dto, Wallet wallet) {
        if (dto == null)
            return null;

        return Account.builder()
                .accountId(dto.getAccountId())
                .wallet(wallet)
                .name(dto.getName())
                .balance(dto.getBalance())
                .overdraft(dto.getOverdraft())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
