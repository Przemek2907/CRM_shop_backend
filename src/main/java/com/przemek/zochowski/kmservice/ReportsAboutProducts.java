package com.przemek.zochowski.kmservice;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.ProductDto;
import com.przemek.zochowski.model.Category;
import com.przemek.zochowski.model.CustomerOrder;
import com.przemek.zochowski.model.EGuarantee;
import com.przemek.zochowski.model.Product;
import com.przemek.zochowski.repository.CustomerOrderRepository;
import com.przemek.zochowski.repository.CustomerOrderRepositoryImpl;
import com.przemek.zochowski.repository.ProductRepository;
import com.przemek.zochowski.repository.ProductRepositoryImpl;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;
import com.przemek.zochowski.service.DataManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportsAboutProducts {


/*    private List<Product> productsWithMaxPricePerCategory = new ArrayList<>();
    private List<Product> productsOrderedByCustomerSpecifiedByUser = new ArrayList<>();*/

    private ProductRepository productRepository = new ProductRepositoryImpl();
    private CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl();
    private ModelMapper modelMapper = new ModelMapper();

    /* THIS METHOD CONNECTS TO THE DATABASE AND COLLECTS THE DATA FROM 4 TABLES, DEPICTING THE NAME, PRODUCER, AND THE PRICE OF THE MOST EXPENSIVE PRODUCT IN EACH CATEGORY
       ALONG WITH THE TOTAL AMOUNT OF ORDERS PLACED FOR SUCH PRODUCT AND THE TOTAL QUANTITY OF THOSE ORDERS. THE DATA IS THEN TRANSFERRED TO AN ARRAY LIST FOR FURTHER USE*/

    //DO ANALIZY NA SPOTKANIU
    public List<DataManager> getProductsOrderedByCustomerSpecifiedByUser (String countryName, int age_min, int age_max) {
            return customerOrderRepository
                    .findAll()
                    .stream()
                    .filter(co -> co.getCustomer().getAge() > age_min)
                    .filter(co -> co.getCustomer().getAge() < age_max)
                    .filter(co -> co.getCustomer().getCountry().getName().equals(countryName))
                    .sorted(Comparator.comparing(co -> co.getProduct().getPrice(), Comparator.reverseOrder()))
                    .map(p -> DataManager.builder()
                                .productName(p.getProduct().getName())
                                .price(p.getProduct().getPrice())
                                .categoryName(p.getProduct().getCategory().getName())
                                .producerName(p.getProduct().getProducer().getName())
                                .countryName(p.getProduct().getProducer().getCountry().getName())
                                .build())
                    .collect(Collectors.toList());
    }


    // ------------------------------------------------------------------------------------------------------------
    // ------------------------- KM
    // ------------------------------------------------------------------------------------------------------------
    public Map<String, DataManager> getMostExpensiveProductsForEachCategory() {
        return productRepository
                .findAll()
                .stream().collect(Collectors.groupingBy(p -> p.getCategory().getName()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue()
                                .stream()
                                .sorted(Comparator.comparing(Product::getPrice, Comparator.reverseOrder()))
                                .findFirst()
                                .map(p -> DataManager.builder()
                                        .productName(p.getName())
                                        .price(p.getPrice())
                                        .producerName(p.getProducer().getName())
                                        .countryName(p.getProducer().getCountry().getName())
                                        .categoryName(p.getCategory().getName())
                                        .orderedQuantity(howManyOrdersForProduct(p.getId()))
                                        .build())
                                .orElse(null)
                ));
    }

    private long howManyOrdersForProduct(Long productId) {
        return customerOrderRepository.findAll()
                .stream()
                .filter(co -> co.getProduct().getId().equals(productId))
                .count();
    }


    //LIST OF PRODUCTS, WHICH HAVE WARRANTY INCLUDED OF A TYPE PROVIDED BY A USER
    public List<Product> getProductWithWarranty (EGuarantee guarantee){
        return productRepository.findAll()
                .stream()
                .filter(p->productRepository.hasGuaranteeSet(p.getId(), guarantee))
                .collect(Collectors.toList());
    }

    public List<DataManager> getProductsOrderedBySpecificCustomer (String customerName, String customerSurname, String countryName){
        return customerOrderRepository.findAll()
                .stream()
                .filter(co -> co.getCustomer().getName().equals(customerName))
                .filter(co -> co.getCustomer().getSurname().equals(customerSurname))
                .filter(co -> co.getCustomer().getCountry().getName().equals(countryName))
                .map( co -> DataManager.builder()
                            .productName( co.getProduct().getName())
                            .producerName(co.getProduct().getProducer().getName())
                            .build())
                .collect(Collectors.toList());
    }
}