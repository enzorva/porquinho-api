package br.com.fiap.porquinho.service.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.porquinho.domainmodel.Account;
import br.com.fiap.porquinho.domainmodel.exceptions.FieldValidationException;
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
        if (account.getName() == null || account.getName().isBlank()) {
            throw new FieldValidationException("name", "O nome da conta não pode estar em branco.");
        }
        if (account.getBalance() == null) {
            throw new FieldValidationException("balance", "O saldo não pode ser nulo.");
        }
        if (account.getWallet() == null) {
            throw new FieldValidationException("wallet", "A conta deve estar vinculada a uma carteira.");
        }
        if (account.getOverdraft() == null) {
            throw new FieldValidationException("overdraft", "O campo overdraft não pode ser nulo.");
        }

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
