package com.przemek.zochowski.repository;

import com.przemek.zochowski.model.EGuarantee;
import com.przemek.zochowski.model.Product;
import com.przemek.zochowski.repository.generic.GenericRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends GenericRepository<Product> {
    Optional<Product> findByName (String name);
    boolean hasGuaranteeSet (Long productId, EGuarantee guarantee);
}
