package br.com.fiap.porquinho.presentation.transferObjects.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.fiap.porquinho.domainmodel.Account;
import br.com.fiap.porquinho.domainmodel.Transaction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateTransactionDTO {

    @NotNull(message = "O ID da conta não pode ser nulo")
    @Positive(message = "O ID da deve ser positivo")
    private Long accountId;

    @NotNull(message = "O valor da transação não pode ser nulo")
    private BigDecimal transactionValue;

    @Size(max = 50, message = "A descrição deve ter no máximo 50 caracteres")
    private String description;

    @NotNull(message = "A data da transação não pode ser nula")
    private LocalDate transactionDate;

    @NotNull(message = "O campo 'hasOccurred' não pode ser nulo")
    private Integer hasOccurred;

    @NotNull(message = "O campo 'isAutoConfirmed' não pode ser nulo")
    private Integer isAutoConfirmed;

    @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres")
    private String observation;

    public static CreateTransactionDTO fromEntity(Transaction transaction) {
        if (transaction == null)
            return null;

        return CreateTransactionDTO.builder()
                .accountId(transaction.getAccount().getAccountId())
                .transactionValue(transaction.getTransactionValue())
                .description(transaction.getDescription())
                .transactionDate(transaction.getTransactionDate())
                .hasOccurred(transaction.getHasOccurred())
                .isAutoConfirmed(transaction.getIsAutoConfirmed())
                .observation(transaction.getObservation())
                .build();
    }

    public static Transaction toEntity(CreateTransactionDTO dto, Account account) {
        if (dto == null)
            return null;

        return Transaction.builder()
                .account(account)
                .transactionValue(dto.getTransactionValue())
                .description(dto.getDescription())
                .transactionDate(dto.getTransactionDate())
                .hasOccurred(dto.getHasOccurred())
                .isAutoConfirmed(dto.getIsAutoConfirmed())
                .observation(dto.getObservation())
                .build();
    }
}
