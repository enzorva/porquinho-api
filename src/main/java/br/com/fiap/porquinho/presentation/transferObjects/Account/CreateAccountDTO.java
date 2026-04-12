package br.com.fiap.porquinho.presentation.transferObjects.Account;

import java.math.BigDecimal;
import br.com.fiap.porquinho.domainmodel.Account;
import br.com.fiap.porquinho.domainmodel.Wallet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateAccountDTO {

    @NotNull(message = "O ID da carteira (walletId) não pode ser nulo")
    @Positive(message = "O ID da carteira (walletId) deve ser positivo")
    private Long walletId;

    @NotBlank(message = "O nome da conta não pode estar em branco")
    @Size(max = 50, message = "O nome da conta deve ter no máximo 50 caracteres")
    private String name;

    @NotNull(message = "O saldo não pode ser nulo")
    @PositiveOrZero(message = "O saldo deve ser maior ou igual a zero")
    private BigDecimal balance;

    @NotNull(message = "O valor de overdraft não pode ser nulo")
    private Integer overdraft;

    public static CreateAccountDTO fromEntity(Account account) {
        if (account == null)
            return null;

        return CreateAccountDTO.builder()
                .walletId(account.getWallet().getWalletId())
                .name(account.getName())
                .balance(account.getBalance())
                .overdraft(account.getOverdraft())
                .build();
    }

    public static Account toEntity(CreateAccountDTO dto, Wallet wallet) {
        if (dto == null)
            return null;

        return Account.builder()
                .wallet(wallet)
                .name(dto.getName())
                .balance(dto.getBalance())
                .overdraft(dto.getOverdraft())
                .build();
    }
}
