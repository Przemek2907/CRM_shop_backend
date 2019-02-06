package com.przemek.zochowski.services.dataInputByUser;

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
    private Integer min_age_range;
    private Integer max_age_range;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal discount;
    private LocalDate localDate;
    private Long theIdOfTheSelectedProduct;
    private Integer choiceId;
    private Integer thresholdAmount;
    private LocalDate localDateTo;
    private BigDecimal orderValue;


    @Override
    public String toString() {

        StringBuilder message = new StringBuilder();
        message.append(customerName == null || customerName.isEmpty() ? "" : "CUSTOMER NAME: " + customerName + "\n");
        message.append(customerSurname == null || customerSurname.isEmpty() ? "" : "CUSTOMER SURNAME: " + customerSurname + "\n");
        message.append(countryName == null || countryName.isEmpty() ? "" : "COUNTRY NAME: " + countryName + "\n");
        message.append(productName == null || productName.isEmpty() ? "" : "PRODUCT NAME: " + productName + "\n");
        message.append(shopName == null || shopName.isEmpty() ? "" : "SHOP NAME: " + shopName + "\n");
        message.append(producerName == null || producerName.isEmpty() ? "" : "PRODUCER NAME: " + producerName + "\n");
        message.append(paymentType == null || paymentType.isEmpty() ? "" : "PAYMENT NAME: " + paymentType + "\n");
        message.append(industryName == null || industryName.isEmpty() ? "" : "INDUSTRY NAME: " + industryName + "\n");
        message.append(orderedQuantity == null ? "" : "ORDERED QUANTITY: " + orderedQuantity + "\n");
        message.append(eGuarantee == null ? "" : "GUARANTEE COMPONENTS: " + eGuarantee + "\n");
        message.append(quantity == null ? "" : "QUANTITY: " + quantity + "\n");
        message.append( age == null ? "" : "AGE: " +  age + "\n");
        message.append(price == null  ? "" : "PRICE: " + price + "\n");
        message.append(discount == null ? "" : "DISCOUNT: " + discount + "\n");
        message.append(localDate == null ? "" : "LOCAL DATE: " + localDate + "\n");
        message.append(theIdOfTheSelectedProduct == null ? "" : "ID OF THE PRODUCT: " + theIdOfTheSelectedProduct + "\n");
        message.append( min_age_range == null ? "" : "MINIMUM AGE RANGE: " + min_age_range + "\n");
        message.append( max_age_range == null ? "" : "MAXIMUM AGE RANGE: " + max_age_range + "\n");

        return message.toString();
    }
}
