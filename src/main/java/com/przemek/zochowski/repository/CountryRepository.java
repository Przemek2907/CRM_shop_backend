package com.przemek.zochowski.repository;

import com.przemek.zochowski.model.Country;
import com.przemek.zochowski.repository.generic.GenericRepository;

import java.util.Optional;

public interface CountryRepository extends GenericRepository<Country> {
    Optional<Country> findByName (String name);
}
