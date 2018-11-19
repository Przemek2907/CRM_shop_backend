package com.przemek.zochowski.repository;

import com.przemek.zochowski.model.Trade;
import com.przemek.zochowski.repository.generic.GenericRepository;

import java.util.Optional;

public interface TradeRepository extends GenericRepository<Trade> {
    Optional<Trade> findByName (String name);
    Optional<Trade> findByNameAndAddIfNotFound(String name);
}
