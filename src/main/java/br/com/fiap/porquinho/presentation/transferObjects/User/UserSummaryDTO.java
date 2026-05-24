package br.com.fiap.porquinho.presentation.transferObjects.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import br.com.fiap.porquinho.domainmodel.Account;
import br.com.fiap.porquinho.domainmodel.Transaction;
import br.com.fiap.porquinho.domainmodel.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserSummaryDTO {

    private Long userId;

    private String name;

    private BigDecimal balance;

    private BigDecimal totalRecipe;

    private BigDecimal totalExpense;

    private List<AccountSummaryDTO> accounts;

    private List<TransactionSummaryDTO> lastTransactions;

    public static UserSummaryDTO fromEntity(User user, List<Account> accounts, List<Transaction> transactions) {
        if (user == null)
            return null;

        // Calcula o saldo total
        BigDecimal totalBalance = accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcula total de receitas e despesas
        BigDecimal totalRecipe = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getTransactionValue().signum() > 0) {
                totalRecipe = totalRecipe.add(transaction.getTransactionValue());
            } else {
                totalExpense = totalExpense.add(transaction.getTransactionValue().abs());
            }
        }

        // Converte contas para AccountSummaryDTO
        List<AccountSummaryDTO> accountDTOs = accounts.stream()
                .map(AccountSummaryDTO::fromEntity)
                .collect(Collectors.toList());

        // Converte transações para TransactionSummaryDTO (últimas 5)
        List<TransactionSummaryDTO> transactionDTOs = transactions.stream()
                .limit(5)
                .map(TransactionSummaryDTO::fromEntity)
                .collect(Collectors.toList());

        return UserSummaryDTO.builder()
                .userId(user.getUserId())
                .name(user.getFullName())
                .balance(totalBalance)
                .totalRecipe(totalRecipe)
                .totalExpense(totalExpense)
                .accounts(accountDTOs)
                .lastTransactions(transactionDTOs)
                .build();
    }
}
