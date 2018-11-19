package com.przemek.zochowski.service.dataFromFile;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.CountryDto;
import com.przemek.zochowski.dto.ProducerDto;
import com.przemek.zochowski.dto.TradeDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.repository.CountryRepository;
import com.przemek.zochowski.repository.CountryRepositoryImpl;
import com.przemek.zochowski.repository.TradeRepository;
import com.przemek.zochowski.repository.TradeRepositoryImpl;

import java.time.LocalDateTime;

public class ProducerDtoParser implements Parser<ProducerDto> {

    CountryRepository countryRepository = new CountryRepositoryImpl();
    TradeRepository tradeRepository = new TradeRepositoryImpl();
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public ProducerDto parse(String text) {
        try{
            if (text == null) {
                throw new NullPointerException("NO DATA HAS BEEN FOUND FOR PRODUCER");
            } else if (!text.matches("([A-Z ]+;[A-Z ]+;[A-Z ]+)")){
                throw new IllegalArgumentException("WRONG DATA FORMAT FOR PRODUCT TABLE");
            }
            String arr[] = text.split(";");
            System.out.println();
            CountryDto countryDto = countryRepository.findByName(arr[1]).map(country -> modelMapper.fromCountryToCountryDto(country))
                    .orElseThrow(NullPointerException::new);
            TradeDto tradeDto = tradeRepository.findByName(arr[2])
                    .map(trade -> modelMapper.fromTradeToTradeDto(trade))
                    .orElseThrow(NullPointerException::new);
            return ProducerDto.builder().name(arr[0]).countryDto(countryDto).tradeDto(tradeDto).build();
        } catch (Exception e){
            throw new MyException(ErrorCode.FILE_PARSER, e.getMessage());
        }
    }
}
