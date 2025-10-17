package br.com.fiap.porquinho.service.Bank;

import java.util.List;
import java.util.Optional;

public interface BankService<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    boolean existsById(ID id);

}
