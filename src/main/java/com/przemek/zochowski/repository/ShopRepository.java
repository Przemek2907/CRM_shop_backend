package com.przemek.zochowski.repository;

import com.przemek.zochowski.model.Shop;
import com.przemek.zochowski.repository.generic.GenericRepository;

import java.util.Optional;

public interface ShopRepository extends GenericRepository<Shop> {
    Optional<Shop> findByName (String name);
    Optional<Shop> findByNameAndCountry (String name, String countryName);
}
