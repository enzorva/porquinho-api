package br.com.fiap.porquinho.domainmodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.porquinho.domainmodel.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

}
