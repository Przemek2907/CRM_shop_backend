package com.przemek.zochowski.services.dataFromFile;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.TradeDto;
import com.przemek.zochowski.model.Trade;
import com.przemek.zochowski.repository.TradeRepository;
import com.przemek.zochowski.repository.TradeRepositoryImpl;
import com.przemek.zochowski.services.dataFromFile.converters.TradeJsonConverter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class TradeJson {
    private Set<TradeDto> tradesDto;
    ModelMapper modelMapper = new ModelMapper();
    TradeRepository tradeRepository = new TradeRepositoryImpl();


    public List<Trade> createListOfUniqueTradeForDB (){
        List<Trade> tradesListToInsert = new ArrayList<>();
        try{
            tradesListToInsert = new TradeJsonConverter(Filenames.TRADE_JSON)
                    .fromJson().orElseThrow(IllegalStateException::new)
                    .getTradesDto()
                    .stream()
                    .map(modelMapper::fromTradeDtoToTrade)
                    .collect(Collectors.toList());

            System.out.println(tradesListToInsert);

            removeDuplicatesFromListComparedToDB(tradesListToInsert);
        } catch (Exception e){
            e.printStackTrace();
        }
        return tradesListToInsert;
    }

    private void removeDuplicatesFromListComparedToDB (List<Trade> tradeList){
        List<Trade> tradesInDB = tradeRepository.findAll();
        System.out.println(tradesInDB);
        for (Trade trade: tradesInDB) {
            tradeList.removeIf(t -> t.getIndustry().equals(trade.getIndustry()));
        }
    }
}
