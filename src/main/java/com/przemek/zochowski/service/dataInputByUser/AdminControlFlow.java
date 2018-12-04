package com.przemek.zochowski.service.dataInputByUser;

import com.przemek.zochowski.kmservice.ReportsAboutCustomer;
import com.przemek.zochowski.kmservice.ReportsAboutProducers;
import com.przemek.zochowski.kmservice.ReportsAboutShops;
import com.przemek.zochowski.model.EGuarantee;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;
import com.przemek.zochowski.kmservice.ReportsAboutProducts;
import com.przemek.zochowski.service.DataManager;
import com.przemek.zochowski.service.ScannerService;
import com.przemek.zochowski.service.businessLogic.ShopServiceLayer;
import com.przemek.zochowski.service.dataFromFile.DataInitializeFromFile;
import com.przemek.zochowski.service.dataFromFile.DataInitializerFromFileService;

import java.util.*;

public class AdminControlFlow {


    private static final String option1 = "1. ADD OR UPDATE THE DATA IN THE SYSTEM LOADING IT FROM FILES? (THE FILES FEEDING THE DATABASE WITH THE TABLES SHOULD BE PLACED IN THE RESOURCES FILE)";
    private static final String option2 = "2. GENERATE A REPORT WITH THE LIST OF PRODUCTS WITH THE MAXIMUM PRICE IN EACH CATEGORY?";
    private static final String option3 = "3. GENERATE A REPORT WITH THE LIST OF PRODUCTS ORDERED BY CLIENTS FROM THE COUNTRY AND AGE RANGE YOU SELECT?";
    private static final String option4 = "4. GENERATE A REPORT WITH THE LIST OF ALL THE PRODUCTS, WHICH HAVE A WARRANTY TYPE SELECTED BY YOU?";
    private static final String option5 = "5. GENERATE A REPORT WITH THE LIST OF SHOPS, WHICH HAVE PRODUCTS FROM A DIFFERENT COUNTRY THAN THE SHOP IS LOCATED IN?";
    private static final String option6 = "6. GENERATE A REPORT WITH THE LIST OF PRODUCERS ACTING IN THE BUSINESS BRANCH YOU HAVE SELECTED, WHO PRODUCED THE " +
            "AMOUNT OF STOCK, HIGHER THAN YOU SELECTED?";
    private static final String option7 = "7. GENERATE A REPORT WITH THE LIST OF ORDERS PLACED WITHIN SELECTED DATE RANGE AND OF A TOTAL VALUE (EXCLUDING DISCOUNT)?";
    private static final String option8 = "8. GENERATE A REPORT WITH THE LIST OF PRODUCTS ORDERED BY A SPECIFIC CUSTOMER?";
    private static final String option9 = "9. GENERATE A REPORT WITH THE LIST OF PRODUCTS MADE IN THE SAME COUNTRY AS THE MOTHER COUNTRY OF A CUSTOMER";
    private static final List<String> optionList = new ArrayList<>(Arrays.asList(option1,option2,option3,option4,option5,option6,option7,option8,option9));
    private ReportsAboutProducts reportsAboutProducts = new ReportsAboutProducts();
    private ReportsAboutShops reportsAboutShops = new ReportsAboutShops();
    private ReportsAboutProducers reportsAboutProducers = new ReportsAboutProducers();
    private ReportsAboutCustomer reportsAboutCustomer = new ReportsAboutCustomer();
    DataInitializerFromFileService dataInitializerFromFileService = new DataInitializeFromFile();
    DataManager dataManager = new DataManager();


    public void adminFlowOfApplication(){
        System.out.println("HELLO ADMIN. WHAT ACTION WOULD YOU LIKE TO TAKE? PICK ONE ACTION");
        System.out.println("WOULD YOU LIKE TO:");
        optionList.stream().forEach(System.out::println);
        dataManager.setChoiceId((ScannerService.getInteger("PICK ONE OPTION BY PROVIDING THE NUMBER ASSIGNED TO IT")));
       checkTheOption();
    }


   private void checkTheOption(){
        int choiceOption = dataManager.getChoiceId();
        switch (choiceOption){
            case 1:
                dataInitializerFromFileService.initializeData();
                break;
            case 2:
                System.out.println("THIS IS THE LIST OF THE MOST EXPENSIVE PRODUCT IN EACH CATEGORY:");
                reportsAboutProducts.getMostExpensiveProductsForEachCategory().forEach((k, v) -> System.out.println(k + " ---> " + v));
                break;
            case 3:
                dataManager.setCountryName(ScannerService.getString("PLEASE PROVIDE THE COUNTRY", "[A-Z ]+"));
                dataManager.setMin_age_range(ScannerService.getInteger("PLEASE PROVIDE THE MINIMUM AGE RANGE"));
                dataManager.setMax_age_range((ScannerService.getInteger("PLEASE PROVIDE THE MAXIMUM AGE RANGE")));
                System.out.println("THIS IS THE LIST OF PRODUCTS ORDERED BY CLIENTS FROM " + dataManager.getCountryName() +", WHO ARE OLDER THAN " + dataManager.getMin_age_range()
                        + ", BUT YOUNGER THAN " + dataManager.getMax_age_range());
                reportsAboutProducts.getProductsOrderedByCustomerSpecifiedByUser(dataManager.getCountryName(), dataManager.getMin_age_range(), dataManager.getMax_age_range())
                .forEach(System.out::println);
                break;
            case 4:
                dataManager.setEGuarantee(Enum.valueOf(EGuarantee.class, ScannerService.getString("PLEASE PROVIDE THE GUARANTEE TYPE WHICH IS ADDED TO SERVICED PRODUCTS", "[A-Z ]+")));
                System.out.println("THIS IS A LIST OF PRODUCTS OF SELECTED GUARANTY TYPE:");
                reportsAboutProducts.getProductWithWarranty(dataManager.getEGuarantee()).forEach(System.out::println);
                break;
            case 5:
                System.out.println("THIS IS A LIST OF SHOPS SELLING PRODUCTS FROM A DIFFERENT COUNTRY THAN THE LOCATION OF THE SHOP:");
                reportsAboutShops.listOfShopsWithProductsFromAbroad().forEach(System.out::println);
                break;
            case 6:
                    dataManager.setIndustryName(ScannerService.getString("PLEASE PROVIDE THE INDUSTRY NAME IN WHICH THE PRODUCERS OPERATE", "[A-Z ]+"));
                    dataManager.setThresholdAmount(ScannerService.getInteger("PLEASE PROVIDE THE THRESHOLD AMOUNT"));
                    reportsAboutProducers.producersWithAmountofStock(dataManager.getIndustryName(), dataManager.getThresholdAmount())
                            .forEach(System.out::println);
                break;
            case 7:
                break;
            case 8:
                dataManager.setCustomerName(ScannerService.getString("PLEASE PROVIDE CUSTOMER NAME", "[A-Z ]+"));
                dataManager.setCustomerSurname(ScannerService.getString("PLEASE PROVIDE CUSTOMER SURNAME", "[A-Z ]+"));
                dataManager.setCountryName(ScannerService.getString("PLEASE PROVIDE COUNTRY NAME", "[A-Z ]+"));
                reportsAboutProducts.getProductsOrderedBySpecificCustomer(dataManager.getCustomerName(),dataManager.getCustomerSurname(), dataManager.getCountryName())
                        .forEach(System.out::println);
                break;
            case 9:
                reportsAboutCustomer.listOfClientsWhoOrderedAproductFromTheSameCountry().forEach(System.out::println);
                break;


            default:
                System.out.println("WRONG INPUT");
        }
    }
}


