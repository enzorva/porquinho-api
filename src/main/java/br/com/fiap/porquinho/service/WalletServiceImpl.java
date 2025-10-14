package br.com.fiap.porquinho.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.porquinho.domainmodel.Wallet;
import br.com.fiap.porquinho.domainmodel.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService<Wallet, Long> {

    private final WalletRepository walletRepository;

    @Override
    public List<Wallet> findAll() {
        return new ArrayList<>(walletRepository.findAll());
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return walletRepository.findById(id);
    }

    @Override
    public Wallet create(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet partialUpdate(Long id, Wallet wallet) {
        if (!walletRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found");
        }

        Wallet walletFromDatabase = walletRepository.findById(id).orElse(null);

        if (wallet.getName() != null && !walletFromDatabase.getName().equals(wallet.getName()))
            walletFromDatabase.setName(wallet.getName());

        if (wallet.getDescription() != null && !walletFromDatabase.getDescription().equals(wallet.getDescription()))
            walletFromDatabase.setDescription(wallet.getDescription());

        return create(walletFromDatabase);
    }

    @Override
    public void removeById(Long id) {
        walletRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return walletRepository.existsById(id);
    }

}
