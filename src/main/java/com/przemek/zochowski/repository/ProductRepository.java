package com.przemek.zochowski.repository;

import com.przemek.zochowski.model.Product;
import com.przemek.zochowski.repository.generic.GenericRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends GenericRepository<Product> {
    Optional<Product> findByName (String name);
    Optional<Product> findByIdWithStocks(Long id);
    List<Product> findProductRequestedByCustomerSelectedByUser();
}
