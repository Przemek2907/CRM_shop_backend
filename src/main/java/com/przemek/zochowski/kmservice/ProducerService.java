package com.przemek.zochowski.kmservice;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.ProducerDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Country;
import com.przemek.zochowski.model.Producer;
import com.przemek.zochowski.model.Trade;
import com.przemek.zochowski.repository.*;
import com.przemek.zochowski.service.DataManager;
import com.przemek.zochowski.service.errors.ErrorService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProducerService {

    ProducerRepository producerRepository = new ProducerRepositoryImpl();
    CountryRepository countryRepository = new CountryRepositoryImpl();
    ErrorService errorService = new ErrorService();
    ModelMapper modelMapper = new ModelMapper();
    TradeRepository tradeRepository = new TradeRepositoryImpl();

    public boolean isProducerInCountry(DataManager dataManager) {
        try {
            if (dataManager.getProducerName() == null) {
                throw new NullPointerException("NAME NOT PROVIDED");
            }

            if (dataManager.getCountryName() == null) {
                throw new NullPointerException("COUNTRY NOT PROVIDED");
            }

            Producer producer = producerRepository
                    .findByName(dataManager.getProducerName())
                    .orElse(null);

            if (producer == null) {
                return false;
            }

            Country country = countryRepository
                    .findByName(dataManager.getCountryName())
                    .orElseThrow(() -> new NullPointerException("COUNTRY NOT FOUND"));

            List<Producer> producers = producerRepository.findAll()
                    .stream()
                    .filter(c -> c.getCountry().getId().equals(country.getId()))
                    .collect(Collectors.toList());

            return producers.contains(producer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.SERVICE, e.getMessage());

        }
    }


    public void addProducerWithCountry(DataManager dataManager) {
        try {

            if (isProducerInCountry(dataManager)) {
                throw new IllegalArgumentException("THIS PRODUCER ALREADY EXISTS IN GIVEN COUNTRY");
            }

            Producer producer = modelMapper.fromProducerDtoToProducer(
                    ProducerDto.builder()
                            .name(dataManager.getProducerName())
                            .build()
            );

            Country country = countryRepository
                    .findByName(dataManager.getCountryName())
                    //orElse(modelMapper.fromCountryDtoToCountry(CountryDto.builder().name(dataManager.getStr3()).build()));
                    .orElseThrow(() -> new NullPointerException("COUNTRY DOESN'T EXIST"));

            Trade trade = tradeRepository.findByNameAndAddIfNotFound(dataManager.getIndustryName())
                    .orElseThrow(() -> new NullPointerException("INDUSTRY DOESN'T EXIST"));


            producer.setCountry(country);
            producer.setTrade(trade);
            producerRepository.addOrUpdate(producer);

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.SERVICE, e.getMessage());
        }
    }
}
