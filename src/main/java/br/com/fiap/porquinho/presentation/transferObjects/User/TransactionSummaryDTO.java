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

    private String name;

    private String icon;

    private String accountName;

    private LocalDate date;

    private BigDecimal value;

    private String type;

    public static TransactionSummaryDTO fromEntity(Transaction transaction) {
        if (transaction == null)
            return null;

        // Determina o tipo da transação (recipe/expense) baseado no sinal do valor
        // Valores positivos = receita, negativos = despesa
        String transactionType = transaction.getTransactionValue().signum() > 0 ? "recipe" : "expense";

        return TransactionSummaryDTO.builder()
                .name(transaction.getDescription())
                .icon(transaction.getCategories() != null && !transaction.getCategories().isEmpty()
                        ? transaction.getCategories().iterator().next().getName()
                        : "default-icon")
                .accountName(transaction.getAccount() != null ? transaction.getAccount().getName() : "")
                .date(transaction.getTransactionDate())
                .value(transaction.getTransactionValue().abs())
                .type(transactionType)
                .build();
    }
}
