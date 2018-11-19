package com.przemek.zochowski.service;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ScannerService {
    private static Scanner scanner = new Scanner(System.in);

    public static String getString(String message, String regex) {
        try {
            String line;
            do {
                System.out.println(message);
                line = scanner.nextLine();
                if (line == null || !line.matches(regex)){
                    System.out.println("WRONG DATA FORMAT. PLEASE TRY AGAIN");
                }
            } while (line == null || !line.matches(regex));
            return line;
        } catch (Exception e) {
            throw new MyException(ErrorCode.VALIDATION, "WRONG DATA FORMAT");
        }
    }

    public static BigDecimal getBigDecimal(String message) {
        try {
            final String regex = "\\d+(.\\d{2})*";
            String line;
            do {
                System.out.println(message);
                line = scanner.nextLine();
                if (line == null || !line.matches(regex)){
                    System.out.println("WRONG DATA FORMAT. PLEASE TRY AGAIN");
                }
            } while (line == null || !line.matches(regex));
            return new BigDecimal(line);
        } catch (Exception e) {
            throw new MyException(ErrorCode.VALIDATION, "WRONG DATA FORMAT. DATA DOES NOT MATCH A NUMBER FORMAT");
        }
    }

    public static Integer getInteger(String message) {
        try{
            final String regex = "[1-9]{1}(\\d)*";
            String line;
            do {
                System.out.println(message);
                line = scanner.nextLine();
                if (line == null || !line.matches(regex)){
                    System.out.println("WRONG DATA FORMAT. PLEASE TRY AGAIN");
                }
            } while (line == null || !line.matches(regex));
            return new Integer(line);
        }catch (Exception e) {
            throw new MyException(ErrorCode.VALIDATION, "WRONG DATA FORMAT. DATA DOES NOT MATCH A NUMBER FORMAT");
        }
    }


    public static Scanner getScanner() {
        return scanner;
    }

    public static void setScanner(Scanner scanner) {
        ScannerService.scanner = scanner;
    }


}
