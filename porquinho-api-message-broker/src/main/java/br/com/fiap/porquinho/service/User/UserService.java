package br.com.fiap.porquinho.service.User;

import java.util.List;
import java.util.Optional;

public interface UserService<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    T create(T o);

    public T partialUpdate(ID id, T o);

    void removeById(ID id);

    boolean existsById(ID id);

}
