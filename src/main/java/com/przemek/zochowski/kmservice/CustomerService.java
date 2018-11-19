package com.przemek.zochowski.kmservice;

import com.przemek.zochowski.dto.CountryDto;
import com.przemek.zochowski.dto.CustomerDto;
import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Country;
import com.przemek.zochowski.model.Customer;
import com.przemek.zochowski.model.Errors;
import com.przemek.zochowski.repository.*;
import com.przemek.zochowski.service.DataManager;
import com.przemek.zochowski.service.errors.ErrorService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private ModelMapper modelMapper = new ModelMapper();
    private ErrorService errorService = new ErrorService();

    public boolean isCustomerInCountry(DataManager dataManager) {
        try {
            if (dataManager.getCustomerName() == null) {
                throw new NullPointerException("NAME IS NULL");
            }
            if (dataManager.getCustomerSurname() == null) {
                throw new NullPointerException("SURNAME IS NULL");
            }
            if (dataManager.getCountryName() == null) {
                throw new NullPointerException("COUNTRY IS NULL");
            }

            Customer customer = customerRepository
                    .findByNameAndSurname(dataManager.getCustomerName(), dataManager.getCustomerSurname())
                    .orElse(null);

            if (customer == null) {
                return false;
            }

            Country country = countryRepository
                    .findByName(dataManager.getCountryName())
                    .orElseThrow(() -> new NullPointerException("COUNTRY IS NULL"));

            List<Customer> customers = customerRepository.findAll()
                    .stream()
                    .filter(c -> c.getCountry().getId().equals(country.getId()))
                    .collect(Collectors.toList());

            return customers.contains(customer);
        } catch (Exception e) {
            throw new MyException(ErrorCode.SERVICE, e.getMessage());

        }
    }

    // wiemy ze str1 to bedzie name, str2 to bedzie surname, str3 kraj
    // a valInt1 to bedzie wiek
    public void addCustomerWithCountry(DataManager dataManager) {
        try {

            if (isCustomerInCountry(dataManager)) {
                throw new IllegalArgumentException("CUSTOMER ALREADY EXISTS IN GIVEN COUNTRY");
            }

            Customer customer = modelMapper.fromCustomerDtoToCustomer(
                    CustomerDto.builder()
                            .name(dataManager.getCustomerName())
                            .surname(dataManager.getCustomerSurname())
                            .age(dataManager.getAge())
                            .build()
            );

            Country country = countryRepository
                    .findByName(dataManager.getCountryName())
                    //orElse(modelMapper.fromCountryDtoToCountry(CountryDto.builder().name(dataManager.getStr3()).build()));
                    .orElseThrow(() -> new NullPointerException("COUNTRY DOESN'T EXIST"));



            customer.setCountry(country);
            customerRepository.addOrUpdate(customer);

        } catch (Exception e) {
            throw new MyException(ErrorCode.SERVICE, e.getMessage());
        }
    }


}
