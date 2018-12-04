package com.przemek.zochowski.kmservice;

import com.przemek.zochowski.model.CustomerOrder;
import com.przemek.zochowski.repository.CustomerOrderRepository;
import com.przemek.zochowski.repository.CustomerOrderRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReportsAboutCustomerOrders {

    CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl();

    public List<CustomerOrder> getTheListOfOrdersFromSpecificPeriodAndAmount (LocalDate dateFrom, LocalDate dateTo, BigDecimal orderValue){
        return customerOrderRepository.findAll()
                .stream()
                .filter(co -> co.getDate().isAfter(dateFrom))
                .filter(co -> co.getDate().isBefore(dateTo))
                .filter( co -> (BigDecimal.valueOf(co.getQuantity()).multiply(co.getProduct().getPrice().multiply(BigDecimal.ONE.subtract(co.getDiscount())))).compareTo(orderValue) ==1)
                .collect(Collectors.toList());
    }
}
