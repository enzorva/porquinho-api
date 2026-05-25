package br.com.fiap.porquinho.domainmodel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.porquinho.domainmodel.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByWallet_User_UserId(Long userId);

}
