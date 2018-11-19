package com.przemek.zochowski.kmservice;

import com.przemek.zochowski.repository.CustomerOrderRepository;
import com.przemek.zochowski.repository.CustomerOrderRepositoryImpl;
import com.przemek.zochowski.service.DataManager;

import java.util.List;
import java.util.stream.Collectors;

public class ReportsAboutCustomer {

    CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl();


    public List<DataManager> listOfClientsWhoOrderedAproductFromTheSameCountry (){
        return customerOrderRepository.findAll().stream()
                .filter(co -> co.getProduct().getProducer().getCountry().equals(co.getCustomer().getCountry().getName()))
                .map(co -> DataManager.builder()
                        .customerName(co.getCustomer().getName())
                        .customerSurname(co.getCustomer().getSurname())
                        .build())
                .collect(Collectors.toList());
    }
}
