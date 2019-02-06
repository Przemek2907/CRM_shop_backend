package com.przemek.zochowski.service.dataInputByUser;

import com.przemek.zochowski.kmservice.CustomerOrderService;
import com.przemek.zochowski.kmservice.CustomerService;
import com.przemek.zochowski.kmservice.ProductService;
import com.przemek.zochowski.kmservice.StockService;
import com.przemek.zochowski.service.DataManager;
import com.przemek.zochowski.service.ScannerService;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;

import java.time.LocalDate;
import java.util.*;

public class ClientFlowNewApproach {

    private String choice = null;
    private static final String option1 = "1. I AM A REGISTERED CLIENT AND I WOULD LIKE TO PLACE AN ORDER.";
    private static final String option2 = "2. I WOULD LIKE TO SET UP A NEW ACCOUNT.";
    private CustomerService customerService = new CustomerService();
    private DataManager dataManager = new DataManager();
    private StockService stockService = new StockService();
    private CustomerOrderService customerOrderService = new CustomerOrderService();







        /*---------------------------------------------------------------------------------------------------------------------------------------------
    ------------------------------------------------------MAIN APPLICATION CONTROL FLOW------------------------------------------------------------
    -----------------------------------------------------------------------------------------------------------------------------------------------
     */

    public void clientFlowOfApplication() {
        System.out.println("WELCOME IN OUR SHOP. ARE YOU A REGISTERED CLIENT OR WOULD YOU LIKE TO SET UP AN ACCOUNT?");
        System.out.println("SELECT THE OPTION BY TYPING IN THE NUMBER IT IS ASSIGNED TO");
        System.out.println(option1);
        System.out.println(option2);
        choice = String.valueOf(ScannerService.getInteger("SELECT ONE OPTION, TYPING THE NUMBER OF OPTION SELECTED"));
        checkTheOption();
    }

    private void checkTheOption() {
        switch (choice.trim()) {
            case "1":
                customerDataValidationInTheDB(dataManager);
                boolean customerVerifiedSuccessfully = customerService.isCustomerInCountry(dataManager);
                if (customerVerifiedSuccessfully) {
                    System.out.println("WELCOME " + dataManager.getCustomerName() + " " + dataManager.getCustomerSurname());
                    userDecision();
                } else if (!customerVerifiedSuccessfully) {
                    System.out.println("THERE IS NO SUCH CUSTOMER IN THE DATABASE. PLEASE TRY TO LOGIN AGAIN OR SET UP A NEW ACCOUNT");
                }
                break;
            case "2":
                customerDataValidationInTheDB(dataManager);
                customerVerifiedSuccessfully = customerService.isCustomerInCountry(dataManager);
                if (!customerVerifiedSuccessfully) {
                    customerService.addCustomerWithCountry(dataManager);
                    System.out.println("WELCOME " + dataManager.getCustomerName() + " " + dataManager.getCustomerSurname() + ". YOUR ACCOUNT " +
                            "HAS BEEN SET UP SUCCESSFULLY");
                    userDecision();
                } else if (customerVerifiedSuccessfully) {
                    System.out.println("A CUSTOMER WITH THE SAME NAME AND SURNAME HAS BEEN FOUND IN THE GIVEN COUNTRY. PLEASE TRY AGAIN OR LOGIN USING EXISTING ACCOUNT");
                }
                break;
        }
    }

/*-------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------VALIDATIONS OF USER INPUT-----------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------------------------------
 */


    //-------------------------THIS METHOD INVOKES PLACING AN ORDER (placingAnOrder METHOD from the CustomerOrderService class)-----------------------------
    private String userDecision() {
        String decision = ScannerService.getString("WOULD LIKE TO PLACE AN ORDER? (Y/N)", "(Y|N)");
        if (decision.toUpperCase().equals("Y")) {
            List<DataManager> listOfProducts = stockService.presentingTheListOfProductsWithStockAmount();
            listOfProducts.forEach(System.out::println);
            gatheringDataFromTheClientForTheOrder();
            customerOrderService.placingAnOrder(dataManager);

        }
        return decision;
    }

    private void gatheringDataFromTheClientForTheOrder() {
        dataManager.setTheIdOfTheSelectedProduct(Long.valueOf(ScannerService.getInteger("SELECT A PRODUCT FROM THE LIST ABOVE BY TYPING IN THE PRODUCT'S ID")));
        if (stockService.presentingTheListOfProductsWithStockAmount().stream().noneMatch(p -> p.getTheIdOfTheSelectedProduct().equals(dataManager.getTheIdOfTheSelectedProduct()))) {
            System.out.println("SELECTED PRODUCT IS NOT AVAILABLE. PLEASE TRY AGAIN");
            gatheringDataFromTheClientForTheOrder();
        } else {
            // IF MORE THAN ONE SHOP HAS THIS PRODUCT, REQUEST ADDITIONAL INFORMATION
            if (stockService.checkingIfProductExistsInDifferentShops(dataManager)) {
                System.out.println("THIS PRODUCT EXIST IN MORE THAN ONE SHOP");
                dataManager.setShopName(ScannerService.getString("PLEASE PROVIDE THE NAME OF SHOP YOU WANT TO ORDER FROM", "[A-Za-z ]+"));
            }
            dataManager.setQuantity(ScannerService.getInteger("PLEASE PROVIDE THE AMOUNT OF THE PRODUCT YOU WOULD LIKE TO ORDER"));

            //IF PRODUCT EXISTS ONLY IN ONE SHOP, GET THE SHOPNAME USING PRODUCT ID
            dataManager.setShopName(stockService.presentingTheListOfProductsWithStockAmount().stream().
                    filter(s -> s.getTheIdOfTheSelectedProduct().equals(dataManager.getTheIdOfTheSelectedProduct()))
                    .findFirst().get().getShopName());
            boolean amountOfStockSufficient = customerOrderService
                    .checkingIfAmountOfProductIsSufficient(dataManager.getTheIdOfTheSelectedProduct(), dataManager.getShopName(), dataManager.getQuantity());
            if (!amountOfStockSufficient) {
                System.out.println("THERE IS NOT ENOUGH OF SELECTED PRODUCT IN STOCK IN THE GIVEN SHOP. PLEASE TRY AGAIN");
                gatheringDataFromTheClientForTheOrder();
            } else {
                dataManager.setPaymentType(ScannerService.getString("TYPE IN THE PAYMENT METHOD OF YOUR CHOICE (CASH, MONEY_TRANSFER, CARD)", "(CASH|MONEY_TRANSFER|CARD)"));
                dataManager.setLocalDate(LocalDate.now());
            }
        }
    }


    private void customerDataValidationInTheDB(DataManager dataManager) {
        dataManager.setCustomerName(ScannerService.getString("PLEASE PROVIDE YOUR NAME:", "[A-Za-z ]+"));
        dataManager.setCustomerSurname(ScannerService.getString("PLEASE PROVIDE YOUR SURNAME:", "[A-Za-z ]+"));
        dataManager.setAge(ScannerService.getInteger("PLEASE PROVIDE YOUR AGE:"));
        while (dataManager.getAge() < 18) {
            System.out.println("INCORRECT DATA. TO SET UP AN ACCOUNT YOUR AGE HAS TO BE GREATER THAN 18. PLEASE TRY AGAIN");
            dataManager.setAge(ScannerService.getInteger("PLEASE PROVIDE YOUR AGE:"));
        }
        dataManager.setCountryName(ScannerService.getString("PLEASE PROVIDE THE COUNTRY YOU ARE FROM:", "[A-Za-z ]+"));
        System.out.println("PLEASE WAIT WHILE WE PROCESS YOUR DATA.");
    }



}
