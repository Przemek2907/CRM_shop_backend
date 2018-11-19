package com.przemek.zochowski.service.dataFromFile;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.*;
import com.przemek.zochowski.repository.*;
import com.przemek.zochowski.repository.generic.GenericRepository;


import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    private ModelMapper modelMapper = new ModelMapper();



    @Override
    public void initializeData() throws MyException {

        List<Category> categories = getItemsFromFile(Filenames.CATEGORY_FILENAME, new CategoryDtoParser())
                .stream()
                .map(a-> modelMapper.fromCategoryDtoToCategory(a)).collect(Collectors.toList());
        addAllItems(categories, categoryRepository);


        List<Country> countries = getItemsFromFile(Filenames.COUNTRY_FILENAME, new CountryDtoParser())
                .stream()
                .map(b-> modelMapper.fromCountryDtoToCountry(b))
                .collect(Collectors.toList());
        addAllItems(countries, countryRepository);


        List<Customer> customers = getItemsFromFile(Filenames.CUSTOMER_FILENAME, new CustomerDtoParser())
                .stream()
                .map(c-> modelMapper.fromCustomerDtoToCustomer(c))
                .collect(Collectors.toList());
        addAllItems(customers, customerRepository);


        List<Trade> tradeList = getItemsFromFile(Filenames.TRADE_FILENAME, new TradeDtoParser())
                .stream()
                .map(d-> modelMapper.fromTradeDtoToTrade(d))
                .collect(Collectors.toList());
        System.out.println(tradeList);
        addAllItems(tradeList, tradeRepository);


        System.out.println("JESTEM TU------------------111111");
        List<Producer> producers = getItemsFromFile(Filenames.PRODUCER_FILENAME, new ProducerDtoParser())
                .stream()
                .map(p -> modelMapper.fromProducerDtoToProducer(p))
                .collect(Collectors.toList());
        addAllItems(producers, producerRepository);

        System.out.println("JESTEM TU------------------22222222");
        List<Shop> shops = getItemsFromFile(Filenames.SHOP_FILENAME, new ShopDtoParser())
                .stream()
                .map(shopDto -> modelMapper.fromShopDtoToShop(shopDto))
                .collect(Collectors.toList());
        addAllItems(shops, shopRepository);


        List<Product> products = getItemsFromFile(Filenames.PRODUCT_FILENAME, new ProductDtoParser())
                .stream()
                .map(p-> modelMapper.fromProductDtoToProduct(p))
                .collect(Collectors.toList());
        addAllItems(products, productRepository);


        List<Stock> stocks = getItemsFromFile(Filenames.STOCK_FILENAME, new StockDtoParser())
                .stream()
                .map(s-> modelMapper.fromStockDtoToStock(s))
                .collect(Collectors.toList());
        addAllItems(stocks,stockRepository);


        List<Payment> payments = Arrays.asList(EPayment.values())
                .stream()
                .map(payment -> Payment.builder().payment(payment).build())
                .collect(Collectors.toList());
        addAllItems(payments, paymentRepository);


        List<CustomerOrder> customerOrders =
                getItemsFromFile(Filenames.CUSTOMER_ORDER_FILENAME, new CustomerOrderDtoParser())
                        .stream()
                        .map(modelMapper::fromCustomerOrderDtoToCustomerOrder)
                        .collect(Collectors.toList());
        addAllItems(customerOrders, customerOrderRepository);

    }











    private <T> List<T> getItemsFromFile(String filename, Parser<T> parser) {
        try (
                FileReader reader = new FileReader(filename);
                Scanner sc = new Scanner(reader);
                Stream<String> lines = Files.lines(Paths.get(filename))) {
            return lines.map(line -> parser.parse(line)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException(ErrorCode.FILE_PARSER, e.getMessage());
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
