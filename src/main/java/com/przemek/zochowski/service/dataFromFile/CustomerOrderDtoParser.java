package com.przemek.zochowski.service.dataFromFile;


import com.przemek.zochowski.dto.*;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.CustomerOrder;
import com.przemek.zochowski.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CustomerOrderDtoParser implements Parser<CustomerOrderDto> {

    //private String dateRegexp = "(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

    CustomerRepository customerRepository = new CustomerRepositoryImpl();
    PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    ProductRepository productRepository = new ProductRepositoryImpl();
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public CustomerOrderDto parse(String text) {

        try{
            if (text==null){
                throw new NullPointerException("No data found in the file");
            } else if (!text.matches("(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]);([0-9]+.[0-9]{2});[0-9]{1,11};[A-Z ]+;\\w+;[A-Z ]+")){
                throw new IllegalArgumentException("Wrong data type in the file to create a database row");
            }
            String arr[] = text.split(";");
            CustomerDto customerDto = customerRepository.findByName(arr[3]).map(c-> modelMapper.fromCustomerToCustomerDto(c))
                    .orElseThrow(NullPointerException::new);
            PaymentDto paymentDto = paymentRepository.findByName(arr[4])
                    .map(p-> modelMapper.fromPaymentToPaymentDto(p))
                    .orElseThrow(NullPointerException::new);
            ProductDto productDto = productRepository.findByName(arr[5])
                    .map(p-> modelMapper.fromProductToProductDto(p))
                    .orElseThrow(NullPointerException::new);


            return CustomerOrderDto.builder().date(LocalDate.parse(arr[0])).discount(BigDecimal.valueOf(Double.parseDouble(arr[1])))
                    .quantity(Integer.parseInt(arr[2])).customerDto(customerDto).paymentDto(paymentDto).productDto(productDto)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(ErrorCode.FILE_PARSER, e.getMessage());
        }

    }
}
