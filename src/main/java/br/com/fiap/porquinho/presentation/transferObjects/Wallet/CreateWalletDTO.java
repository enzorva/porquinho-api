package br.com.fiap.porquinho.presentation.transferObjects.Wallet;

import br.com.fiap.porquinho.domainmodel.User;
import br.com.fiap.porquinho.domainmodel.Wallet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateWalletDTO {

    @NotNull(message = "O ID do usuário não pode ser nulo")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long userId;

    @NotBlank(message = "O nome da carteira não pode estar em branco")
    @Size(min = 2, max = 50, message = "O nome da carteira deve ter entre 2 e 50 caracteres")
    private String name;

    @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres")
    private String description;

    public static CreateWalletDTO fromEntity(Wallet wallet) {
        if (wallet == null)
            return null;

        return CreateWalletDTO.builder()
                .userId(wallet.getUser().getUserId())
                .name(wallet.getName())
                .description(wallet.getDescription())
                .build();
    }

    public static Wallet toEntity(CreateWalletDTO dto, User user) {
        if (dto == null)
            return null;

        return Wallet.builder()
                .user(user)
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
