package com.przemek.zochowski.services.entityService;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.ProducerDto;
import com.przemek.zochowski.model.Stock;
import com.przemek.zochowski.repository.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportsAboutProducers {
    private CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl();
    private StockRepository stockRepository = new StockRepositoryImpl();
    private ModelMapper modelMapper = new ModelMapper();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();


    // WROCIC DO PODPUNKTU e Z WARSTWY SERWISOWEJ

    // ZEBY TO ZROBIC POTRZBUJĘ:
    //METODY LICZACEJ ilosc produktow per producer_id (tabela product i stock) - mam metodę w StockRepoImpl


   /* Pobranie z bazy danych producentów o nazwie branży podanej przez użytkownika,
    którzy wyprodukowali produkty o łącznej ilości sztuk większej niż liczba podana przez użytkownika.*/
    public List<Map.Entry<ProducerDto, Integer>> producersWithAmountofStock (String industryName, int thresholdAmount){
       return stockRepository
                .findAll()
                .stream()
                .filter(s -> s.getProduct().getProducer().getTrade().getIndustry().equals(industryName))
                .collect(Collectors.groupingBy(s -> s.getProduct().getProducer()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> modelMapper.fromProducerToProducerDto(e.getKey()),
                        e -> e.getValue().stream().map(Stock::getQuantity).reduce(0, Integer::sum)
                ))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > thresholdAmount)
               .collect(Collectors.toList());
    }

}