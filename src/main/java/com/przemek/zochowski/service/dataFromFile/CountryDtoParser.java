package com.przemek.zochowski.service.dataFromFile;

import com.przemek.zochowski.dto.CountryDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Country;

import java.time.LocalDateTime;


public class CountryDtoParser implements Parser<CountryDto> {
    @Override
    public CountryDto parse(String text) {
        try{
            if (text == null){
                throw new NullPointerException("NO DATA FOR COUNTRY HAS BEEN FOUND");
            } else if (!text.matches("[A-Z ]+")){
                throw new IllegalArgumentException("WRONG DATA FORMAT FOR COUNTRY TABLE");
            }
            return CountryDto.builder().name(text).build();
        } catch (Exception e){
            throw new MyException(ErrorCode.FILE_PARSER, e.getMessage());
        }
    }
}
