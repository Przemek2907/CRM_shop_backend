package com.przemek.zochowski.kmservice;


import com.przemek.zochowski.repository.StockRepository;
import com.przemek.zochowski.repository.StockRepositoryImpl;
import com.przemek.zochowski.service.DataManager;

import java.util.List;
import java.util.stream.Collectors;

public class ReportsAboutShops {

    StockRepository stockRepository = new StockRepositoryImpl();

    public List<DataManager> listOfShopsWithProductsFromAbroad (){
         return stockRepository.findAll().stream()
                .filter(s -> !s.getShop().getCountry().getId().equals(s.getProduct().getProducer().getCountry().getId()))
                .map(s -> DataManager.builder()
                .shopName(s.getShop().getName())
                        .build())
                .collect(Collectors.toList());

    }
}
