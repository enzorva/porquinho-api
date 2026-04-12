package br.com.fiap.porquinho.presentation.transferObjects.Transaction;

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

    private @Setter @Getter BigDecimal transactionValue;

    private @Setter @Getter String description;

    private @Setter @Getter LocalDate transactionDate;

    private @Setter @Getter Integer hasOccurred;

    private @Setter @Getter Integer isAutoConfirmed;

    private @Setter @Getter String observation;

    private @Setter @Getter LocalDateTime createdAt;

    private @Setter @Getter LocalDateTime updatedAt;

    public static TransactionDTO fromEntity(Transaction transaction) {
        if (transaction == null)
            return null;

        return TransactionDTO.builder()
                .transactionId(transaction.getTransactionId())
                .transactionValue(transaction.getTransactionValue())
                .description(transaction.getDescription())
                .transactionDate(transaction.getTransactionDate())
                .hasOccurred(transaction.getHasOccurred())
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
                .transactionValue(dto.getTransactionValue())
                .description(dto.getDescription())
                .transactionDate(dto.getTransactionDate())
                .hasOccurred(dto.getHasOccurred())
                .isAutoConfirmed(dto.getIsAutoConfirmed())
                .observation(dto.getObservation())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

}
