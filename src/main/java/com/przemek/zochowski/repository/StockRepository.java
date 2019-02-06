package com.przemek.zochowski.repository;

import com.przemek.zochowski.model.Stock;
import com.przemek.zochowski.repository.generic.GenericRepository;

import java.util.Map;
import java.util.Optional;

public interface StockRepository extends GenericRepository<Stock> {
    Map<String, Integer> findAmountOfStockByTradeName (String industryName, int thresholdAmount);
    Long findStockIdByProductId(Long product_id);
    Long findStockIdByProductName(String productName);

}
