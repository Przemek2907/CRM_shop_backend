package com.przemek.zochowski.repository;

import com.przemek.zochowski.model.Category;
import com.przemek.zochowski.repository.generic.GenericRepository;

import java.util.Optional;

public interface CategoryRepository extends GenericRepository<Category> {
    Optional<Category> findByName (String name);
}
