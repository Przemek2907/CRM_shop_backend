package com.przemek.zochowski.kmservice;

import com.przemek.zochowski.repository.CustomerOrderRepository;
import com.przemek.zochowski.repository.CustomerOrderRepositoryImpl;
import com.przemek.zochowski.repository.StockRepository;
import com.przemek.zochowski.repository.StockRepositoryImpl;
import com.przemek.zochowski.service.DataManager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ReportsAboutProducers {
    CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl();
    StockRepository stockRepository = new StockRepositoryImpl();


    // WROCIC DO PODPUNKTU E Z WARSTWY SERWISOWEJ


/*
    public List<DataManager> listOfProducers (String industryName, int quantity){
        return customerOrderRepository.findAll()
                .stream()
                .filter(p -> p.getProduct().getProducer().getTrade().getIndustry().equals(industryName))
                .map( p -> DataManager.builder()
                        .producerName(p.getProduct().getProducer().getName())
                        .quantity(p.getQuantity())
                        .build())
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.groupingBy( c->c, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .filter(s -> s.getValue() > Long.valueOf(quantity))
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue(),
                        (v1, v2) -> v1,
                        () -> new LinkedHashMap<>()
                ));
    }


    private int totalQuantityOfProduct(int marginQuantity) {
        return stockRepository.findAll()
                .stream()

    }*/
}
