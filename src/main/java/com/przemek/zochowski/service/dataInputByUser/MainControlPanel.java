package com.przemek.zochowski.service.dataInputByUser;

import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.service.ScannerService;
import com.przemek.zochowski.service.errors.ErrorService;

import java.util.Scanner;

public class MainControlPanel {

    ClientFlowNewApproach clientFlowNewApproach = new ClientFlowNewApproach();
    ErrorService errorService = new ErrorService();
    private static final String table1 = "KLIENT";
    private static final String table2 = "SHOP";
    private static final String table3 = "PRODUCTENT";
    private static final String table4 = "PRODUCT";
    private static final String table5 = "STOCK";
    private static final String table6 = "CUSTOMER_ORDER";


    public void userInteractionWithTheApp (){
        boolean exit = false;
        System.out.println("WELCOME IN THE MINI-CRM APP");
        System.out.println("ARE YOU A SYSTEM ADMIN OR A CLIENT?");
        do {
            try {
                String choice1 = ScannerService.getString("CHOOSE ONE OPTION:\n 1 - SYSTEM ADMIN \n 2 - CLIENT", "(1|2)");
                if (choice1.trim().equals("SYSTEM ADMIN") || choice1.trim().equals("1")) {
                    // INCLUDE HERE THE PART OF ADMIN APPLICATION
                } else if (choice1.trim().equals("CLIENT") || choice1.trim().equals("2")) {
                    clientFlowNewApproach.clientFlowOfApplication();
                }
                System.out.println("DO YOU WANT TO CLOSE THE APP? (Y/N)");
                String choiceToContinueAppRunning = ScannerService.getString("", "(Y|N)");
                exit = choiceToContinueAppRunning.toUpperCase().equals("Y");
            } catch (MyException e) {
                errorService.addError(e);
            }
        }while (!exit);
    }

}



/*
 System.out.println("TO WHICH TABLE DO YOU WANT TO LOAD THE DATA?");
         System.out.println("SELECT THE TABLE: " + table1 +"," + table2 + "," + table3 + "," + table4 + "," + table5 + "," + table6);
         String choice2 = userInput();
         switch (choice2.toUpperCase()){
         case table1:
         // metoda obslugujaca dodawanie klientow do tabeli (osobna klasa, czy tutaj?)
         break;
         case table2:
         //metoda obslugujaca dodawanie sklepów do tabeli (osobna klasa, czy tutaj?)
         break;
         case table3:
         //metoda obslugujaca dodawanie producentów do tabeli (osobna klasa, czy tutaj?)
         break;
         case table4:
         //metoda obslugujaca dodawanie produktów do tabeli (osobna klasa, czy tutaj?)
         break;
         case table5:
         //metoda obslugujaca dodawanie towarówklientow do tabeli (osobna klasa, czy tutaj?)
         break;
         case table6:
         //metoda obslugujaca dodawanie zamówień do tabeli (osobna klasa, czy tutaj?)
         break;

default:
        System.out.println("WRONG INPUT");
        }
        System.out.println("DO YOU WANT TO CLOSE THE APP? (Y/N)");
        String choiceToContinueAppRunning = userInput();
        exit = choiceToContinueAppRunning.toUpperCase().equals("Y");*/
