package com.przemek.zochowski.repository;

import com.przemek.zochowski.model.Stock;
import com.przemek.zochowski.repository.generic.GenericRepository;

import java.util.Optional;

public interface StockRepository extends GenericRepository<Stock> {
    int findAmountOfStockByProductId (int id);
    Long findStockIdByProductId(Long product_id);

}
