package br.com.fiap.porquinho.domainmodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.porquinho.domainmodel.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
