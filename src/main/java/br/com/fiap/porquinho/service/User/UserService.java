package br.com.fiap.porquinho.service.User;

import java.util.List;
import java.util.Optional;

import br.com.fiap.porquinho.domainmodel.Account;
import br.com.fiap.porquinho.domainmodel.Transaction;
import br.com.fiap.porquinho.presentation.transferObjects.User.UserSummaryDTO;

public interface UserService<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    T create(T o);

    public T partialUpdate(ID id, T o);

    void removeById(ID id);

    boolean existsById(ID id);

    List<Account> findUserAccounts(ID userId);

    List<Transaction> findUserLastTransactions(ID userId, int limit);

    UserSummaryDTO getUserSummary(ID userId);

}
