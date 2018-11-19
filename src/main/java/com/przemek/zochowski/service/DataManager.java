package com.przemek.zochowski.service;

import com.przemek.zochowski.model.EGuarantee;
import com.przemek.zochowski.model.Stock;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataManager {
    private String customerName;
    private String customerSurname;
    private String countryName;
    private String productName;
    private String shopName;
    private String producerName;
    private String paymentType;
    private String industryName;
    private String categoryName;
    private Long orderedQuantity;
    private EGuarantee eGuarantee;
    private Integer age;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal discount;
    private LocalDate localDate;
    private Long theIdOfTheSelectedProduct;


    @Override
    public String toString() {
        String s =  customerName + '\''
                 + customerSurname + '\''
                 + countryName + '\'' + productName + '\'' + shopName + '\''
               + producerName + '\''
                 + paymentType + '\'' +
                 industryName + '\'' +
               categoryName + '\'' +
                 orderedQuantity + '\'' +
                 eGuarantee + '\'' +
                 age + '\''
                 + quantity + '\'' +
                  price + '\'' +
                  discount + '\'' +
                 localDate + '\'' +
                  theIdOfTheSelectedProduct;

        String result = s.replaceAll("null", "");
        return result;
    }
}

//  List<Object> listOfFields = Arrays.asList(
//              customerName,customerSurname,countryName,productName,shopName,producerName,paymentType,industryName,categoryName,orderedQuantity,
//                eGuarantee,age,quantity,price,discount,localDate,theIdOfTheSelectedProduct);