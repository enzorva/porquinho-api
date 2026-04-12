package br.com.fiap.porquinho.presentation.transferObjects.Bank;

import br.com.fiap.porquinho.domainmodel.Bank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BankDTO {

    private Long bankId;

    private String name;

    private String code;

    private String logoUrl;

    public static BankDTO fromEntity(Bank bank) {
        if (bank == null)
            return null;

        return BankDTO.builder()
                .bankId(bank.getBankId())
                .name(bank.getName())
                .code(bank.getCode())
                .logoUrl(bank.getLogoUrl())
                .build();
    }

    public static Bank toEntity(BankDTO dto) {
        if (dto == null)
            return null;

        return Bank.builder()
                .bankId(dto.getBankId())
                .name(dto.getName())
                .code(dto.getCode())
                .logoUrl(dto.getLogoUrl())
                .build();
    }
}
