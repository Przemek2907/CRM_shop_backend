package com.przemek.zochowski.services.dataFromFile;

import com.przemek.zochowski.dto.CountryDto;
import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.ProducerDto;
import com.przemek.zochowski.dto.TradeDto;
import com.przemek.zochowski.model.Producer;
import com.przemek.zochowski.repository.*;
import com.przemek.zochowski.services.dataFromFile.converters.ProducerJsonConverter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class ProducerJson {
    private Set<ProducerDto> producersDto;
    ModelMapper modelMapper = new ModelMapper();
    CountryRepository countryRepository = new CountryRepositoryImpl();
    TradeRepository tradeRepository = new TradeRepositoryImpl();
    ProducerRepository producerRepository = new ProducerRepositoryImpl();

    public List<Producer> createListOfUniqueProducersForDB (){
        List<Producer> producerListToInsert = new ArrayList<>();
        try{
            producerListToInsert = new ProducerJsonConverter(Filenames.PRODUCER_JSON)
                    .fromJson()
                    .orElseThrow(IllegalAccessException::new)
                    .getProducersDto()
                    .stream()
                    .map(modelMapper::fromProducerDtoToProducer)
                    .collect(Collectors.toList());

            compareTheListWithTheDB(producerListToInsert);

        }catch (Exception e){
            e.printStackTrace();
        }
        return producerListToInsert;
    }

    private void compareTheListWithTheDB (List<Producer> producersToBePutToDB){
        List<Producer> producersInTheDB = producerRepository.findAll();
        for (Producer producer: producersInTheDB){
            producersToBePutToDB.removeIf(c -> c.getName().equals(producer.getName())
                && c.getTrade().getIndustry().equals(producer.getTrade().getIndustry())
                && c.getCountry().getName().equals(producer.getCountry().getName()));
        }
    }

    public List<Producer> addProducerWithRelatedTradeAndCountry (List<Producer> producersToBePutInDB){
        List<ProducerDto> producerDtoList = new ArrayList<>();
        List<Producer> producersFinalListToDB;

        for (Producer producer: producersToBePutInDB){
            CountryDto countryDto = countryRepository.findByName(producer.getCountry().getName())
                    .map(c -> modelMapper.fromCountryToCountryDto(c))
                    .orElseThrow(NullPointerException::new);

            TradeDto tradeDto = tradeRepository.findByName(producer.getTrade().getIndustry())
                    .map(t -> modelMapper.fromTradeToTradeDto(t))
                    .orElseThrow(NullPointerException::new);

            ProducerDto producerDto = ProducerDto.builder()
                    .name(producer.getName())
                    .countryDto(countryDto)
                    .tradeDto(tradeDto)
                    .build();

            producerDtoList.add(producerDto);
        }

        producersFinalListToDB = producerDtoList.stream().map(modelMapper::fromProducerDtoToProducer)
                .collect(Collectors.toList());
        return producersFinalListToDB;
    }
}
