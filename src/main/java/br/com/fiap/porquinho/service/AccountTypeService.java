package br.com.fiap.porquinho.service;

import java.util.List;
import java.util.Optional;

public interface AccountTypeService<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    T create(T o);

    boolean existsById(ID id);

}
