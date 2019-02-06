package com.przemek.zochowski.services.dataFromFile;

import com.przemek.zochowski.dto.CountryDto;
import com.przemek.zochowski.dto.CustomerDto;
import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.model.Customer;
import com.przemek.zochowski.repository.CountryRepository;
import com.przemek.zochowski.repository.CountryRepositoryImpl;
import com.przemek.zochowski.repository.CustomerRepository;
import com.przemek.zochowski.repository.CustomerRepositoryImpl;
import com.przemek.zochowski.services.dataFromFile.converters.CustomerJsonConverter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class CustomerJson {
    private Set<CustomerDto> customersDto;
    ModelMapper modelMapper = new ModelMapper();
    CustomerRepository customerRepository = new CustomerRepositoryImpl();
    CountryRepository countryRepository = new CountryRepositoryImpl();

    public List<Customer> createListOfUniqueCustomersforDB (){
        List<Customer> customersListToInsert = new ArrayList<>();
        try{
            customersListToInsert = new CustomerJsonConverter(Filenames.CUSTOMER_JSON)
                    .fromJson().orElseThrow(IllegalStateException::new)
                    .getCustomersDto()
                    .stream()
                    .map(modelMapper::fromCustomerDtoToCustomer)
                    .collect(Collectors.toList());

            compareTheListWithTheDB(customersListToInsert);
        } catch (Exception e){
            e.printStackTrace();
        }
        return customersListToInsert;
    }

    private void compareTheListWithTheDB (List<Customer> customersToBePutToDB){
        List<Customer> customersInTheDB = customerRepository.findAll();
        for (Customer customer: customersInTheDB){
            customersToBePutToDB.removeIf( c-> c.getName().equals(customer.getName())
                    && c.getSurname().equals(customer.getSurname())
                    && c.getCountry().getName().equals(customer.getCountry().getName()));
        }
    }

    public List<Customer> addCustomerWithCountryID(List<Customer> customersToBePutIntoDB){
        List<CustomerDto> customerDtoList = new ArrayList<>();
        List<Customer> customersFinalListToDB;
        for (Customer customer: customersToBePutIntoDB){
            CountryDto countryDto = countryRepository.findByName(customer.getCountry().getName())
                    .map(c -> modelMapper.fromCountryToCountryDto(c))
                    .orElseThrow(NullPointerException::new);

            CustomerDto customerDto = CustomerDto.builder()
                    .name(customer.getName())
                    .surname(customer.getSurname())
                    .age(customer.getAge())
                    .countryDTO(countryDto)
                    .build();

            customerDtoList.add(customerDto);
        }

       customersFinalListToDB = customerDtoList.stream().map(modelMapper::fromCustomerDtoToCustomer)
                .collect(Collectors.toList());
        return customersFinalListToDB;
    }

}
