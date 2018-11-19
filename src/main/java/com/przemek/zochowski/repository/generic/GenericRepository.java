package com.przemek.zochowski.repository.generic;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenericRepository<T> {
    void addOrUpdate(T t);
    void delete(Long id);
    void deleteAll();
    Optional<T> findOneById(Long id);
    List<T> findAll();
}
