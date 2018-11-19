package com.przemek.zochowski.service.dataInputByUser;

import com.przemek.zochowski.repository.generic.AbstractGenericRepository;
import com.przemek.zochowski.kmservice.ReportsAboutProducts;
import com.przemek.zochowski.service.businessLogic.ShopServiceLayer;
import com.przemek.zochowski.service.dataFromFile.DataInitializeFromFile;
import com.przemek.zochowski.service.dataFromFile.DataInitializerFromFileService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AdminControlFlow extends AbstractGenericRepository {

    private static Scanner input = new Scanner(System.in);
    private String choice = null;
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
    private static final String option10 = "10. REMOVE ALL THE DATA FROM THE SYSTEM";
    private static final List<String> optionList = new ArrayList<>(Arrays.asList(option1,option2,option3,option4,option5,option6,option7,option8,option9,option10));
    private ReportsAboutProducts reportsAboutProducts = new ReportsAboutProducts();
    private ShopServiceLayer shopServiceLayer = new ShopServiceLayer();
    DataInitializerFromFileService dataInitializerFromFileService = new DataInitializeFromFile();


    public void adminFlowOfApplication(){
        System.out.println("HELLO ADMIN. WHAT ACTION WOULD YOU LIKE TO TAKE? PICK ONE ACTION");
        System.out.println("WOULD YOU LIKE TO:");
        optionList.stream().forEach(System.out::println);
        System.out.println("SELECT ONE OPTION, TYPING THE NUMBER OF OPTION SELECTED");
        choice = userInput();
       /* checkTheOption();*/
    }


    private String userInput() {
        String userInput = input.nextLine();
            while ((!userInput.trim().matches("([1-9]{1}|10)"))){
                System.out.println("YOU HAVE NOT PROVIDED A CORRECT OPTION NUMBER. TRY AGAIN");
                userInput = input.nextLine();
            }
            return userInput;
        }



   /* private void checkTheOption(){
        switch (choice.toUpperCase().trim()){
            case "1":
                dataInitializerFromFileService.initializeData();

                break;
            case "2":
              reportsAboutProducts.getListOfProductsWithMaxPricePerCategory();
                break;
            case "3":
                reportsAboutProducts.getListOfProducts();
                break;
            case "4":
                //metoda obslugujaca dodawanie produktów do tabeli (osobna klasa, czy tutaj?)
                break;
            case "5":
                //metoda obslugujaca dodawanie towarówklientow do tabeli (osobna klasa, czy tutaj?)
                break;
            case "6":
                //metoda obslugujaca dodawanie zamówień do tabeli (osobna klasa, czy tutaj?)
                break;
            case "7":
                break;
            case "8":
                break;
            case "9":
                break;
            case "10":
                deleteAll();
                break;

            default:
                System.out.println("WRONG INPUT");
        }
    }
*/

}


