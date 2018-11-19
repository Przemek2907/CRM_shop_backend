package com.przemek.zochowski.repository;

import com.przemek.zochowski.model.Customer;
import com.przemek.zochowski.repository.generic.GenericRepository;

import java.util.Optional;

public interface CustomerRepository extends GenericRepository<Customer> {
        Optional<Customer> findByName (String name);
        Optional<Customer> findByNameAndSurname (String name, String surname);
}
