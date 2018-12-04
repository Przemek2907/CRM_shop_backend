package com.przemek.zochowski;


import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.kmservice.*;
import com.przemek.zochowski.model.CustomerOrder;
import com.przemek.zochowski.model.EPayment;
import com.przemek.zochowski.model.Product;
import com.przemek.zochowski.repository.generic.DbConnection;
import com.przemek.zochowski.service.DataManager;
import com.przemek.zochowski.service.businessLogic.ShopServiceLayer;
import com.przemek.zochowski.service.dataInputByUser.MainControlPanel;
import com.przemek.zochowski.service.errors.ErrorService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        ErrorService errorService = new ErrorService();
        //ReportsAboutProducts productsServiceLayer = new ReportsAboutProducts();
        ShopServiceLayer shopServiceLayer = new ShopServiceLayer();
        MainControlPanel mainControlPanel = new MainControlPanel();


        //DataInitializeFromFile dataInitializeFromFile = new DataInitializeFromFile();
        //dataInitializeFromFile.initializeData();
        // DataCleanupService dataCleaner = new DataCleanupServiceImpl();
        // dataCleaner.removeAll();
        //productsServiceLayer.getListOfProductsWithMaxPricePerCategory();
        //productsServiceLayer.getListOfProducts();
        //shopServiceLayer.getListofProductsWithAdifferentCountryThanTheShop();
        mainControlPanel.userInteractionWithTheApp();
      /*  } catch (MyException e) {

                System.out.println(e.getErrorMessage());
                System.out.println(e.getTableName());
        } finally {
            DbConnection.getInstance().getSessionFactory().close();
        }*/

     /*   CustomerService customerService = new CustomerService();
        // System.out.println(customerService.isCustomerInCountry("ADAM", "KOWALSKI", "POLAND"));
        customerService.addCustomerWithCountry(DataManager.builder()
                .str1("FILIPE")
                .str2("LUIZ")
                .str3("BRAZIL")
                .valInt1(65)
                .build());*/

      /*  ShopService shopService = new ShopService();
        shopService.addShopWithCountry(DataManager.builder().str1("HUWAWEII").str2("CHINA").build());*/

     /*   ProducerService producerService = new ProducerService();
        producerService.addProducerWithCountry(DataManager.builder().str1("PLAY").str2("XXX").str3("BELGIUM").build());*/
/*
        ProductService productService = new ProductService();
        productService.addNewProduct(DataManager.builder().str1("MACBOOK PRO").bigDecimalVal1(BigDecimal.valueOf(12500.00)).str2("PLAY")
                .str3("ELECTRONICS")
                .str4("EXCHANGE")
                .build());*/


        //
        //(String name, String surname, String productName, int requestedAmount, LocalDate localDate, String payment)
 /* CustomerOrderService customerOrderService = new CustomerOrderService();
        customerOrderService
                .placingAnOrder(DataManager.builder().customerName("NARUTO")
                        .customerSurname("UZUMAKI")
                        .productName("PLAYSTATION")
                        .shopName("EURO RTV AGD")
                        .quantity(2)
                        .paymentType("CASH")
                        .localDate(LocalDate.now())
                        .build());*/

/*        ReportsAboutShops reportsAboutShops = new ReportsAboutShops();

        List<DataManager> listData = reportsAboutShops.listOfShopsWithProductsFromAbroad();
        listData.forEach(System.out::println);

*/

/*ReportsAboutProducts reportsAboutProducts = new ReportsAboutProducts();

        List<Product> productWithGuarantee = reportsAboutProducts.getProductWithWarranty("EXCHANGE");
        productWithGuarantee.forEach(System.out::println);*/

/*

        List<DataManager> products = reportsAboutProducts.getProductsOrderedBySpecificCustomer("PRZEMYSLAW", "ZOCHOWSKI", "POLAND");
        products.forEach(System.out::println);

        ReportsAboutCustomer reportsAboutCustomer = new ReportsAboutCustomer();


        System.out.println("asdasdsad");
        List<DataManager> listOfCustomers = reportsAboutCustomer.listOfClientsWhoOrderedAproductFromTheSameCountry();
        listOfCustomers.forEach(System.out::println);
*/


/*        List<Product> list = reportsAboutProducts.getProductsWithMaxPricePerCategory();
        for (Product listItem : list){
            System.out.println(listItem);
        }*/



 /* ;*/

      /*List<DataManager> list1 = reportsAboutProducts.getProductsOrderedByCustomerSpecifiedByUser("JAPAN", 25, 45);
      list1.forEach(System.out::println);*/





  /*  StockService stockService = new StockService();
    stockService.addNewStockItem(DataManager.builder().str1("TILES").str2("WALL MART").str3("UNITED STATES").valInt3(1500).build());*/


        // WROCIC NA SPOTKANIU


        // serwis - punkt E
       /* ReportsAboutProducers reportsAboutProducers = new ReportsAboutProducers();*/

        //działa - ale potestować jeszcze
         //System.out.println(reportsAboutProducers.producersWithAmountofStock("TECHNOLOGY", 200));

        //serwis - punkt F
/*
     ReportsAboutCustomerOrders reportsAboutCustomerOrders = new ReportsAboutCustomerOrders();
        List<CustomerOrder> listOfOrders = reportsAboutCustomerOrders.getTheListOfOrdersFromSpecificPeriodAndAmount(LocalDate.of(2018,11,4), LocalDate.of(2018,11,15), BigDecimal.valueOf(13500));
        listOfOrders.forEach(System.out::println);*/

        // serwis - punkt H - poprawione, działa
/*
        ReportsAboutCustomer reportsAboutCustomer = new ReportsAboutCustomer();
        reportsAboutCustomer
                .listOfClientsWhoOrderedAproductFromTheSameCountry()
                .forEach(System.out::println);*/
    }

}

