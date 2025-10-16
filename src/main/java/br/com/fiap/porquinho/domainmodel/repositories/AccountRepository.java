package br.com.fiap.porquinho.domainmodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.porquinho.domainmodel.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
