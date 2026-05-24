package br.com.fiap.porquinho.service.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.porquinho.domainmodel.Account;
import br.com.fiap.porquinho.domainmodel.Transaction;
import br.com.fiap.porquinho.domainmodel.User;
import br.com.fiap.porquinho.domainmodel.exceptions.FieldValidationException;
import br.com.fiap.porquinho.domainmodel.repositories.UserRepository;
import br.com.fiap.porquinho.presentation.transferObjects.User.UserSummaryDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService<User, Long> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User create(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(other -> {
            throw new FieldValidationException("email", "Este e-mail já está em uso.");
        });

        user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));

        return userRepository.save(user);
    }

    @Override
    public User partialUpdate(Long id, User user) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found");
        }

        userRepository.findByEmail(user.getEmail()).ifPresent(other -> {
            if (!other.getUserId().equals(user.getUserId())) {
                throw new FieldValidationException("email", "Este e-mail já está em uso.");
            }
        });

        User userFromDatabase = userRepository.findById(id).orElse(null);

        if (user.getFullName() != null && !userFromDatabase.getFullName().equals(user.getFullName()))
            userFromDatabase.setFullName(user.getFullName());

        if (user.getEmail() != null && !userFromDatabase.getEmail().equals(user.getEmail()))
            userFromDatabase.setEmail(user.getEmail());

        if (user.getHashedPassword() != null && !userFromDatabase.getHashedPassword().equals(user.getHashedPassword()))
            userFromDatabase.setHashedPassword(user.getHashedPassword());

        if (user.getIncome() != null && !userFromDatabase.getIncome().equals(user.getIncome()))
            userFromDatabase.setIncome(user.getIncome());

        if (user.getPhoneNumber() != null && !userFromDatabase.getPhoneNumber().equals(user.getPhoneNumber()))
            userFromDatabase.setPhoneNumber(user.getPhoneNumber());

        if (user.getBirthday() != null && !userFromDatabase.getBirthday().equals(user.getBirthday()))
            userFromDatabase.setBirthday(user.getBirthday());

        if (user.getProfilePictureUrl() != null
                && !userFromDatabase.getProfilePictureUrl().equals(user.getProfilePictureUrl()))
            userFromDatabase.setProfilePictureUrl(user.getProfilePictureUrl());

        return create(userFromDatabase);
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public List<Account> findUserAccounts(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return Collections.emptyList();
        }

        return userRepository.findAccountsByUserId(userId);
    }

    @Override
    public List<Transaction> findUserLastTransactions(Long userId, int limit) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return Collections.emptyList();
        }

        List<Transaction> transactions = userRepository.findLastTransactionsByUserId(userId);
        // Limita os resultados em memória
        return transactions.stream()
                .limit(limit)
                .toList();
    }

    @Override
    public UserSummaryDTO getUserSummary(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return null;
        }

        List<Account> accounts = findUserAccounts(userId);
        List<Transaction> transactions = findUserLastTransactions(userId, 5);

        return UserSummaryDTO.fromEntity(user.get(), accounts, transactions);
    }

}
