package com.przemek.zochowski.service.dataFromFile;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.CountryDto;
import com.przemek.zochowski.dto.ShopDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Shop;
import com.przemek.zochowski.repository.CountryRepository;
import com.przemek.zochowski.repository.CountryRepositoryImpl;

import java.time.LocalDateTime;


public class ShopDtoParser implements Parser<ShopDto> {

    CountryRepository countryRepository = new CountryRepositoryImpl();
    ModelMapper modelMapper = new ModelMapper();
    @Override
    public ShopDto parse(String text) {
        try{
            if (text == null){
                throw new NullPointerException("NO DATA FOUND FOR SHOP TABLE");
            } else if (!text.matches("[A-Z ]+;[A-Z ]+")){
                throw new IllegalArgumentException("WRONG DATA FORMAT FOR SHOP TABLE");
            }
            String arr[] = text.split(";");
            CountryDto countryDto = countryRepository
                    .findByName(arr[1])
                    .map(country -> modelMapper.fromCountryToCountryDto(country))
                    .orElseThrow(NullPointerException::new);
            return ShopDto.builder().name(arr[0]).countryDto(countryDto).build();
        } catch (Exception e){
            throw new MyException(ErrorCode.FILE_PARSER, e.getMessage());
        }
    }
}
