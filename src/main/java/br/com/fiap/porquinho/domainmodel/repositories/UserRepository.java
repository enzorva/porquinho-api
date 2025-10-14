package br.com.fiap.porquinho.domainmodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.porquinho.domainmodel.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
