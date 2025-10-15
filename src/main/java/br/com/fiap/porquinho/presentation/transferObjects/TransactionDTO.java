package br.com.fiap.porquinho.presentation.transferObjects;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.fiap.porquinho.domainmodel.Transaction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TransactionDTO {

    private @Setter @Getter Long transactionId;

    private @Setter @Getter BigDecimal value;

    private @Setter @Getter String description;

    private @Setter @Getter LocalDate date;

    private @Setter @Getter Integer hasOccured;

    private @Setter @Getter Integer isAutoConfirmed;

    private @Setter @Getter String observation;

    private @Setter @Getter LocalDateTime createdAt;

    private @Setter @Getter LocalDateTime updatedAt;

    public static TransactionDTO fromEntity(Transaction transaction) {
        if (transaction == null)
            return null;

        return TransactionDTO.builder()
                .transactionId(transaction.getTransactionId())
                .value(transaction.getValue())
                .description(transaction.getDescription())
                .date(transaction.getDate())
                .hasOccured(transaction.getHasOccured())
                .isAutoConfirmed(transaction.getIsAutoConfirmed())
                .observation(transaction.getObservation())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }

    public static Transaction toEntity(TransactionDTO dto) {
        if (dto == null)
            return null;

        return Transaction.builder()
                .transactionId(dto.getTransactionId())
                .value(dto.getValue())
                .description(dto.getDescription())
                .date(dto.getDate())
                .hasOccured(dto.getHasOccured())
                .isAutoConfirmed(dto.getIsAutoConfirmed())
                .observation(dto.getObservation())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

}
