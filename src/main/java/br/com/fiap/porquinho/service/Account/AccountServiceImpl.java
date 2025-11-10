package br.com.fiap.porquinho.service.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.porquinho.domainmodel.Account;
import br.com.fiap.porquinho.domainmodel.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService<Account, Long> {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accountRepository.findAll());
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account partialUpdate(Long id, Account account) {
        if (!accountRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found");
        }

        Account accountFromDatabase = accountRepository.findById(id).orElse(null);

        if (account.getName() != null && !accountFromDatabase.getName().equals(account.getName()))
            accountFromDatabase.setName(account.getName());

        if (account.getOverdraft() != null && !accountFromDatabase.getOverdraft().equals(account.getOverdraft()))
            accountFromDatabase.setOverdraft(account.getOverdraft());

        if (account.getBalance() != null && !accountFromDatabase.getBalance().equals(account.getBalance()))
            accountFromDatabase.setBalance(account.getBalance());

        return create(accountFromDatabase);
    }

    @Override
    public void removeById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return accountRepository.existsById(id);
    }

}
