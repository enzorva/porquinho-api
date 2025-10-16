package br.com.fiap.porquinho.presentation.transferObjects;

import br.com.fiap.porquinho.domainmodel.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateAccountTypeDTO {

    @NotBlank(message = "O nome do tipo de conta não pode estar em branco")
    @Size(max = 20, message = "O nome deve ter no máximo 20 caracteres")
    private String name;

    @NotBlank(message = "O rótulo do tipo de conta não pode estar em branco")
    @Size(max = 50, message = "O rótulo deve ter no máximo 50 caracteres")
    private String label;

    @NotBlank(message = "O comportamento não pode estar em branco")
    @Pattern(regexp = "^(wallet|banking_account)$", 
             message = "O comportamento deve ser 'wallet' ou 'banking_account'")
    private String behavior;


    public static CreateAccountTypeDTO fromEntity(AccountType accountType) {
        if (accountType == null)
            return null;

        return CreateAccountTypeDTO.builder()
                .name(accountType.getName())
                .label(accountType.getLabel())
                .behavior(accountType.getBehavior())
                .build();
    }

    public static AccountType toEntity(CreateAccountTypeDTO dto) {
        if (dto == null)
            return null;

        return AccountType.builder()
                .name(dto.getName())
                .label(dto.getLabel())
                .behavior(dto.getBehavior())
                .build();
    }
}
