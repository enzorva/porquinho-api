package br.com.fiap.porquinho.presentation.transferObjects;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    // Nao sei quanto a linha 21

    @NotNull(message = "O valor da transação não pode ser nulo")
    @Positive(message = "O valor da transação deve ser maior que zero")
    private BigDecimal value;

    @Size(max = 50, message = "A descrição deve ter no máximo 50 caracteres")
    private String description;

    @NotNull(message = "A data da transação não pode ser nula")
    private LocalDate date;

    @NotNull(message = "O campo 'hasOccurred' não pode ser nulo")
    private Integer hasOccured;

    @NotNull(message = "O campo 'isAutoConfirmed' não pode ser nulo")
    private Integer isAutoConfirmed;

    @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres")
    private String observation;

    // @NotBlank(message = "O nome completo não pode estar em branco")
    // private @Setter @Getter LocalDateTime createdAt;

    // private @Setter @Getter LocalDateTime updatedAt;

    public static CreateTransactionDTO fromEntity(Transaction transaction) {
        if (transaction == null)
            return null;

        return CreateTransactionDTO.builder()
                .value(transaction.getValue())
                .description(transaction.getDescription())
                .date(transaction.getDate())
                .hasOccured(transaction.getHasOccured())
                .isAutoConfirmed(transaction.getIsAutoConfirmed())
                .observation(transaction.getObservation())
                .build();
    }

    public static Transaction toEntity(CreateTransactionDTO dto) {
        if (dto == null)
            return null;

        return Transaction.builder()
                .value(dto.getValue())
                .description(dto.getDescription())
                .date(dto.getDate())
                .hasOccured(dto.getHasOccured())
                .isAutoConfirmed(dto.getIsAutoConfirmed())
                .observation(dto.getObservation())
                .build();
    }
    }

