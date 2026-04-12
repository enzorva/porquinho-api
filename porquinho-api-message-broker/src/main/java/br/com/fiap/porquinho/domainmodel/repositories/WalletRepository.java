package br.com.fiap.porquinho.domainmodel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.porquinho.domainmodel.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findByUserUserId(Long userId);
}
