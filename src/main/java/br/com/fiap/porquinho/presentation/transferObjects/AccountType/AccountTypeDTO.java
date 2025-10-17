package br.com.fiap.porquinho.presentation.transferObjects.AccountType;

import br.com.fiap.porquinho.domainmodel.AccountType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AccountTypeDTO {

    private Long accountTypeId;

    private String name;

    private String label;

    private String behavior;

    public static AccountTypeDTO fromEntity(AccountType accountType) {
        if (accountType == null)
            return null;

        return AccountTypeDTO.builder()
                .accountTypeId(accountType.getAccountTypeId())
                .name(accountType.getName())
                .label(accountType.getLabel())
                .behavior(accountType.getBehavior())
                .build();
    }

    public static AccountType toEntity(AccountTypeDTO dto) {
        if (dto == null)
            return null;

        return AccountType.builder()
                .accountTypeId(dto.getAccountTypeId())
                .name(dto.getName())
                .label(dto.getLabel())
                .behavior(dto.getBehavior())
                .build();
    }
}
