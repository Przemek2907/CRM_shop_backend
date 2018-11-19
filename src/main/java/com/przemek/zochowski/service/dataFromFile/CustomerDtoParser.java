package com.przemek.zochowski.service.dataFromFile;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.CountryDto;
import com.przemek.zochowski.dto.CustomerDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Customer;
import com.przemek.zochowski.repository.CountryRepository;
import com.przemek.zochowski.repository.CountryRepositoryImpl;

import java.time.LocalDateTime;

public class CustomerDtoParser implements Parser<CustomerDto> {

            CountryRepository countryRepository = new CountryRepositoryImpl();
            ModelMapper modelMapper = new ModelMapper();

    @Override
    public CustomerDto parse(String text) {
            try {
                if (text == null ) {
                    throw new NullPointerException("NO DATA HAS BEEN FOUND FOR THE CUSTOMER TABLE TO LOAD IN");
                }  else if (!text.matches("([A-Z ]+);([A-Z ]+);[0-9]{2};([A-Z ]+)")) {
                    throw new IllegalArgumentException("WRONG CUSTOMER DATA FORMAT");
                }
                String [] arr = text.split(";");
                CountryDto countryDto = countryRepository.findByName(arr[3])
                        .map(c-> modelMapper.fromCountryToCountryDto(c))
                        .orElseThrow(NullPointerException::new);
                return CustomerDto.builder().name(arr[0]).surname(arr[1]).age(Integer.parseInt(arr[2])).countryDTO(countryDto).build();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new MyException(ErrorCode.FILE_PARSER, e.getMessage());
            }
        }
}
