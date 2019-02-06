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
    private PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    private CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl();



    @Override
    public void initializeDataFromJson() throws MyException {
        try {

            // CATEGORY FROM JSON ADDED TO THE DATABASE
            CategoryJson categoryJson = new CategoryJson();
            List<Category> categories = categoryJson.createListOfUniqueCategoriesforDB();
            System.out.println(categories);
            addAllItems(categories, categoryRepository);

            // COUNTRY FROM JSON ADDED TO THE DATABASE

            System.out.println("2 ------------------------------------------------------");
            CountryJson countryJson = new CountryJson();
            List<Country> countries = countryJson.createListOfUniqueCountriesforDB();
            System.out.println(countries);
            addAllItems(countries, countryRepository);

            // CUSTOMER FROM JSON ADDED TO THE DATABASE
            System.out.println("3 ------------------------------------------------------");
            CustomerJson customerJson = new CustomerJson();
            List<Customer> customers = customerJson.addCustomerWithCountryID(customerJson.createListOfUniqueCustomersforDB());
            System.out.println(customers);

            addAllItems(customers, customerRepository);


            // TRADE FROM JSON ADDED TO THE DATABASE
            System.out.println("4 ------------------------------------------------------");
            TradeJson tradeJson = new TradeJson();
            List<Trade> trades = tradeJson.createListOfUniqueTradeForDB();
            addAllItems(trades, tradeRepository);

            System.out.println("5 ------------------------------------------------------");

            // PRODUCER FROM JSON ADDED TO THE DATABASE

            ProducerJson producerJson = new ProducerJson();
            List<Producer> producers = producerJson.addProducerWithRelatedTradeAndCountry(producerJson.createListOfUniqueProducersForDB());
            addAllItems(producers, producerRepository);
            System.out.println(producers);

            System.out.println("6 ------------------------------------------------------");

            // SHOP FROM JSON ADDED TO THE DATABASE

           ShopJson shopJson = new ShopJson();
           List<Shop> shops = shopJson.addShopWithRelatedCountry(shopJson.createListOfUniqueShopssForDB());
           addAllItems(shops, shopRepository);

            // PRODUCT FROM JSON ADDED TO THE DATABASE

            System.out.println("7 ------------------------------------------------------");

            ProductJson productJson = new ProductJson();
            List<Product> products = productJson.productListWithRelatedCategoryAndProducer(productJson.createListOfUniqueProducts());

            addAllItems(products, productRepository);
            System.out.println(products);

            // STOCK FROM JSON ADDED TO THE DATABASE

            System.out.println("8 ------------------------------------------------------");

            StockJson stockJson = new StockJson();
            List<Stock> stocks = stockJson.addStockWithRelatedData(stockJson.createListOfUniqueStocksforDB());
            addAllItems(stocks, stockRepository);

            // PAYMENT FROM JSON READ FROM THE EPAYMENT CLASS

            /*System.out.println("9 ------------------------------------------------------");*/

          /*  List<Payment> payments = Arrays.asList(EPayment.values())
                    .stream()
                    .map(payment -> Payment.builder().payment(payment).build())
                    .collect(Collectors.toList());
            addAllItems(payments, paymentRepository);
            System.out.println(payments);*/

            // CUSTOMER ORDER FROM JSON ADDED TO THE DATABASE
            System.out.println("10 ------------------------------------------------------");

            CustomerOrderJson customerOrderJson = new CustomerOrderJson();
            List<CustomerOrder> customerOrders = customerOrderJson.addingCustomerOrdersWithRelatedDataToDB(customerOrderJson.createListOfOrdersFroDB());

            addAllItems(customerOrders, customerOrderRepository);

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
