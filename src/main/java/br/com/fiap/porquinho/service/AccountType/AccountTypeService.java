package br.com.fiap.porquinho.service.AccountType;

import java.util.List;
import java.util.Optional;

public interface AccountTypeService<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    boolean existsById(ID id);

}
