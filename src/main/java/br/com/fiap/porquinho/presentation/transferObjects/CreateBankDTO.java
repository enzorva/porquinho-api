package br.com.fiap.porquinho.presentation.transferObjects;

import br.com.fiap.porquinho.domainmodel.Bank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateBankDTO {

    @NotBlank(message = "O nome do banco não pode estar em branco")
    @Size(max = 100, message = "O nome do banco deve ter no máximo 100 caracteres")
    private String name;

    @Size(max = 10, message = "O código do banco deve ter no máximo 10 caracteres")
    private String code;

    @Size(max = 255, message = "A URL do logo deve ter no máximo 255 caracteres")
    private String logoUrl;


    public static CreateBankDTO fromEntity(Bank bank) {
        if (bank == null)
            return null;

        return CreateBankDTO.builder()
                .name(bank.getName())
                .code(bank.getCode())
                .logoUrl(bank.getLogoUrl())
                .build();
    }

    public static Bank toEntity(CreateBankDTO dto) {
        if (dto == null)
            return null;

        return Bank.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .logoUrl(dto.getLogoUrl())
                .build();
    }
}
