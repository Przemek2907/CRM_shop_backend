package com.przemek.zochowski.repository;

import com.przemek.zochowski.model.Producer;
import com.przemek.zochowski.repository.generic.GenericRepository;

import java.util.Optional;

public interface ProducerRepository extends GenericRepository<Producer> {
    Optional<Producer> findByName (String name);
}
