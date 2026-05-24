package br.com.fiap.porquinho.domainmodel.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.porquinho.domainmodel.Account;
import br.com.fiap.porquinho.domainmodel.Transaction;
import br.com.fiap.porquinho.domainmodel.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    @Query("SELECT a FROM Account a " +
            "JOIN Wallet w ON a.wallet.walletId = w.walletId " +
            "WHERE w.user.userId = :userId")
    public List<Account> findAccountsByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT t FROM Transaction t " +
            "JOIN Account a ON t.account.accountId = a.accountId " +
            "JOIN Wallet w ON a.wallet.walletId = w.walletId " +
            "WHERE w.user.userId = :userId " +
            "ORDER BY t.transactionDate DESC", nativeQuery = false)
    public List<Transaction> findLastTransactionsByUserId(@Param("userId") Long userId);
}
