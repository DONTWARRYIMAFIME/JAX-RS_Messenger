package by.rest.messenger.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, U> {

    T save(T t);

    T update(T t);

    Optional<T> find(U id);

    List<T> findAll();

    void delete(U id);

    long count();

}