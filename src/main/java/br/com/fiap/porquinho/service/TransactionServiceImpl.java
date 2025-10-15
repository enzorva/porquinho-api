package br.com.fiap.porquinho.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.porquinho.domainmodel.Transaction;
import br.com.fiap.porquinho.domainmodel.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService<Transaction, Long> {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactionRepository.findAll());
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction create(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction partialUpdate(Long id, Transaction transaction) {
        if (!transactionRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found");
        }

        Transaction transactionFromDatabase = transactionRepository.findById(id).orElse(null);

        if (transaction.getValue() != null && !transactionFromDatabase.getValue().equals(transaction.getValue()))
            transactionFromDatabase.setValue(transaction.getValue());

        if (transaction.getDescription() != null && !transactionFromDatabase.getDescription().equals(transaction.getDescription()))
            transactionFromDatabase.setDescription(transaction.getDescription());

        if (transaction.getDate() != null && !transactionFromDatabase.getDate().equals(transaction.getDate()))
            transactionFromDatabase.setDate(transaction.getDate());

        // if (transaction.getHasOccured() != null && !transactionFromDatabase.getHasOccured().equals(transaction.getHasOccured()))
        //     transactionFromDatabase.setHasOccured(transaction.getHasOccured());

        // if (transaction.getIsAutoConfirmed() != null && !transactionFromDatabase.getIsAutoConfirmed().equals(transaction.getIsAutoConfirmed()))
        //     transactionFromDatabase.setIsAutoConfirmed(transaction.getIsAutoConfirmed());

        if (transaction.getObservation() != null && !transactionFromDatabase.getObservation().equals(transaction.getObservation()))
            transactionFromDatabase.setObservation(transaction.getObservation());

        return create(transactionFromDatabase);
    }

    @Override
    public void removeById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return transactionRepository.existsById(id);
    }

}
