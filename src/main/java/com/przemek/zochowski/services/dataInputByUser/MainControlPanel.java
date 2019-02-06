package com.przemek.zochowski.services.dataInputByUser;

import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.repository.generic.DbConnection;
import com.przemek.zochowski.services.errors.ErrorService;

public class MainControlPanel {

    ClientFlowNewApproach clientFlowNewApproach = new ClientFlowNewApproach();
    AdminControlFlow adminControlFlow = new AdminControlFlow();
    ErrorService errorService = new ErrorService();


    public void userInteractionWithTheApp (){
        boolean exit = false;
        System.out.println("WELCOME IN THE MINI-CRM APP");
        System.out.println("ARE YOU A SYSTEM ADMIN OR A CLIENT?");
        do {
            try {
                String choice1 = ScannerService.getString("CHOOSE ONE OPTION:\n 1 - SYSTEM ADMIN \n 2 - CLIENT", "(1|2)");
                if (choice1.trim().equals("SYSTEM ADMIN") || choice1.trim().equals("1")) {
                    adminControlFlow.adminFlowOfApplication();
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
        DbConnection.getInstance().close();
    }

}
