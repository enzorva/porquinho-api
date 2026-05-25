package br.com.fiap.porquinho.presentation.transferObjects.User;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.fiap.porquinho.domainmodel.Transaction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TransactionSummaryDTO {

    private Long id;

    private String name;

    private String icon;

    private String accountName;

    private LocalDate date;

    private BigDecimal value;

    private String type;

    public static TransactionSummaryDTO fromEntity(Transaction transaction) {
        if (transaction == null)
            return null;

        String transactionType = transaction.getTransactionValue().signum() > 0 ? "recipe" : "expense";

        return TransactionSummaryDTO.builder()
                .id(transaction.getTransactionId())
                .name(transaction.getDescription())
                .accountName(transaction.getAccount() != null ? transaction.getAccount().getName() : "")
                .date(transaction.getTransactionDate())
                .value(transaction.getTransactionValue().abs())
                .type(transactionType)
                .build();
    }

}
