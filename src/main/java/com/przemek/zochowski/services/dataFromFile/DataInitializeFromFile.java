package com.przemek.zochowski.services.dataFromFile;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.*;
import com.przemek.zochowski.repository.*;
import com.przemek.zochowski.repository.generic.GenericRepository;


import java.util.*;

public class DataInitializeFromFile implements DataInitializerFromFileService {

    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private TradeRepository tradeRepository = new TradeRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private ShopRepository shopRepository = new ShopRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private StockRepository stockRepository = new StockRepositoryImpl();
    private CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl();



    @Override
    public void initializeDataFromJson() throws MyException {
        try {

            // CATEGORY FROM JSON ADDED TO THE DATABASE
            CategoryJson categoryJson = new CategoryJson();
            List<Category> categories = categoryJson.createListOfUniqueCategoriesforDB();
            System.out.println(categories);
            addAllItems(categories, categoryRepository);
            System.out.println("1 - CATEGORIES ADDED TO THE DATABASE FROM JSON FILE");

            // COUNTRY FROM JSON ADDED TO THE DATABASE

            CountryJson countryJson = new CountryJson();
            List<Country> countries = countryJson.createListOfUniqueCountriesforDB();
            System.out.println(countries);
            addAllItems(countries, countryRepository);
            System.out.println("2 - COUNTRIES ADDED TO THE DATABASE FROM JSON FILE");

            // CUSTOMER FROM JSON ADDED TO THE DATABASE
            CustomerJson customerJson = new CustomerJson();
            List<Customer> customers = customerJson.addCustomerWithCountryID(customerJson.createListOfUniqueCustomersforDB());
            System.out.println(customers);

            addAllItems(customers, customerRepository);
            System.out.println("3 - CUSTOMERS ADDED TO THE DATABASE FROM JSON FILE");


            // TRADE FROM JSON ADDED TO THE DATABASE
            TradeJson tradeJson = new TradeJson();
            List<Trade> trades = tradeJson.createListOfUniqueTradeForDB();
            addAllItems(trades, tradeRepository);
            System.out.println("4 - TRADES ADDED TO THE DATABASE FROM JSON FILE");


            // PRODUCER FROM JSON ADDED TO THE DATABASE

            ProducerJson producerJson = new ProducerJson();
            List<Producer> producers = producerJson.addProducerWithRelatedTradeAndCountry(producerJson.createListOfUniqueProducersForDB());
            addAllItems(producers, producerRepository);
            System.out.println(producers);

            System.out.println("5 - MANUFACTURERS ADDED TO THE DATABASE FROM JSON FILE");

            // SHOP FROM JSON ADDED TO THE DATABASE

           ShopJson shopJson = new ShopJson();
           List<Shop> shops = shopJson.addShopWithRelatedCountry(shopJson.createListOfUniqueShopssForDB());
           addAllItems(shops, shopRepository);
           System.out.println("6 - SHOPS ADDED TO THE DATABASE FROM JSON FILE");

            // PRODUCT FROM JSON ADDED TO THE DATABASE

            ProductJson productJson = new ProductJson();
            List<Product> products = productJson.productListWithRelatedCategoryAndProducer(productJson.createListOfUniqueProducts());

            addAllItems(products, productRepository);
            System.out.println(products);
            System.out.println("7 - PRODUCTS ADDED TO THE DATABASE FROM JSON FILE");

            // STOCK FROM JSON ADDED TO THE DATABASE

            StockJson stockJson = new StockJson();
            List<Stock> stocks = stockJson.addStockWithRelatedData(stockJson.createListOfUniqueStocksforDB());
            addAllItems(stocks, stockRepository);
            System.out.println("8 - PRODUCTS IN STOCK ADDED TO THE DATABASE FROM JSON FILE");


            CustomerOrderJson customerOrderJson = new CustomerOrderJson();
            List<CustomerOrder> customerOrders = customerOrderJson.addingCustomerOrdersWithRelatedDataToDB(customerOrderJson.createListOfOrdersFroDB());
            addAllItems(customerOrders, customerOrderRepository);

            System.out.println("9 - ILLUSTRATIVE CUSTOMER ORDERS ADDED TO THE DATABASE FROM JSON FILE");

        } catch (Exception e){
            e.printStackTrace();
            throw new MyException(ErrorCode.DATA_UPLOAD_FROM_FILE, e.getMessage());
        }
    }


    private <T> void addAllItems(List<T> items, GenericRepository<T> repository) {
        if (items != null) {
            items.forEach(i -> repository.addOrUpdate(i));
        }
    }

    private <T> void addAllEnums(String s, GenericRepository<T> repository){
        if (s != null){
        }
    }


}
