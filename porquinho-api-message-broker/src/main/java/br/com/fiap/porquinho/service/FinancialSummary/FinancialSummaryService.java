package br.com.fiap.porquinho.service.FinancialSummary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.porquinho.domainmodel.Account;
import br.com.fiap.porquinho.domainmodel.Transaction;
import br.com.fiap.porquinho.domainmodel.Wallet;
import br.com.fiap.porquinho.domainmodel.repositories.AccountRepository;
import br.com.fiap.porquinho.domainmodel.repositories.TransactionRepository;
import br.com.fiap.porquinho.domainmodel.repositories.WalletRepository;
import br.com.fiap.porquinho.presentation.transferObjects.FinancialSummary.AccountSummaryDTO;
import br.com.fiap.porquinho.presentation.transferObjects.FinancialSummary.FinancialSummaryDTO;
import br.com.fiap.porquinho.presentation.transferObjects.FinancialSummary.WalletSummaryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinancialSummaryService {

    private final WalletRepository walletRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public FinancialSummaryDTO generateSummary(Long userId) {
        log.info("Gerando resumo financeiro para o usuário {}", userId);

        List<Wallet> wallets = walletRepository.findByUserUserId(userId);

        BigDecimal totalBalance = BigDecimal.ZERO;
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;
        int totalAccounts = 0;
        int totalTransactions = 0;
        List<WalletSummaryDTO> walletSummaries = new ArrayList<>();

        for (Wallet wallet : wallets) {
            List<Account> accounts = accountRepository.findByWalletWalletId(wallet.getWalletId());
            BigDecimal walletBalance = BigDecimal.ZERO;
            List<AccountSummaryDTO> accountSummaries = new ArrayList<>();

            for (Account account : accounts) {
                List<Transaction> transactions = transactionRepository.findByAccountAccountId(account.getAccountId());

                BigDecimal accountIncome = BigDecimal.ZERO;
                BigDecimal accountExpenses = BigDecimal.ZERO;

                for (Transaction transaction : transactions) {
                    if (transaction.getTransactionValue().signum() > 0) {
                        accountIncome = accountIncome.add(transaction.getTransactionValue());
                    } else {
                        accountExpenses = accountExpenses.add(transaction.getTransactionValue().abs());
                    }
                }

                accountSummaries.add(AccountSummaryDTO.builder()
                        .accountId(account.getAccountId())
                        .accountName(account.getName())
                        .balance(account.getBalance())
                        .transactionCount(transactions.size())
                        .totalIncome(accountIncome)
                        .totalExpenses(accountExpenses)
                        .build());

                walletBalance = walletBalance.add(account.getBalance());
                totalIncome = totalIncome.add(accountIncome);
                totalExpenses = totalExpenses.add(accountExpenses);
                totalTransactions += transactions.size();
            }

            totalAccounts += accounts.size();
            totalBalance = totalBalance.add(walletBalance);

            walletSummaries.add(WalletSummaryDTO.builder()
                    .walletId(wallet.getWalletId())
                    .walletName(wallet.getName())
                    .totalAccounts(accounts.size())
                    .totalBalance(walletBalance)
                    .accounts(accountSummaries)
                    .build());
        }

        return FinancialSummaryDTO.builder()
                .userId(userId)
                .totalWallets(wallets.size())
                .totalAccounts(totalAccounts)
                .totalTransactions(totalTransactions)
                .totalBalance(totalBalance)
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netBalance(totalIncome.subtract(totalExpenses))
                .wallets(walletSummaries)
                .build();
    }
}
