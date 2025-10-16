package br.com.fiap.porquinho.domainmodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.porquinho.domainmodel.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {

}
