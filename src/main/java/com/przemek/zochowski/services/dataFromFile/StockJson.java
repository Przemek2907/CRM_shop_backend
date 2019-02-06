package com.przemek.zochowski.services.dataFromFile;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.ProductDto;
import com.przemek.zochowski.dto.ShopDto;
import com.przemek.zochowski.dto.StockDto;
import com.przemek.zochowski.model.Stock;
import com.przemek.zochowski.repository.*;
import com.przemek.zochowski.services.dataFromFile.converters.StockJsonConverter;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class StockJson {
    private Set<StockDto> stocksDto;
    ModelMapper modelMapper = new ModelMapper();
    StockRepository stockRepository = new StockRepositoryImpl();
    ProductRepository productRepository = new ProductRepositoryImpl();
    ShopRepository shopRepository = new ShopRepositoryImpl();



    public List<Stock> createListOfUniqueStocksforDB () {
        List<Stock> stockListToInsert = new ArrayList<>();
        try {
            stockListToInsert =  new StockJsonConverter(Filenames.STOCK_JSON)
                    .fromJson().orElseThrow(IllegalStateException::new)
                    .getStocksDto()
                    .stream()
                    .map(modelMapper::fromStockDtoToStock)
                    .collect(Collectors.toList());

            addQuantityIfTheSameStock(stockListToInsert);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stockListToInsert;
    }

    private void addQuantityIfTheSameStock(List<Stock> stocksToBePutToDB) {
        List<Stock> stocksInTheDB = stockRepository.findAll();
        for (Stock stock : stocksInTheDB) {
            Iterator<Stock> stockIterator = stocksToBePutToDB.iterator();
            while (stockIterator.hasNext()) {
                Stock stockTemp = stockIterator.next();
                if (stockTemp.getProduct().getName().equals(stock.getProduct().getName()) &&
                        stockTemp.getShop().getName().equals(stock.getShop().getName())) {

                    int updatedAmount = stockTemp.getQuantity() + stock.getQuantity();
                    stock.setQuantity(updatedAmount);
                    stockRepository.addOrUpdate(stock);
                }
            }

                stocksToBePutToDB.removeIf(s -> s.getProduct().getName().equals(stock.getProduct().getName())
                        && s.getShop().getName().equals(stock.getShop().getName()));
            }
        }

    public List<Stock> addStockWithRelatedData (List<Stock> stocksToBePutInTheDB){
        List<StockDto> stockDtoList = new ArrayList<>();
        List<Stock> stockFinalList;
        for (Stock stock : stocksToBePutInTheDB){
            ProductDto productDto = productRepository.findByName(stock.getProduct().getName())
                    .map(p -> modelMapper.fromProductToProductDto(p))
                    .orElseThrow(NullPointerException::new);

            ShopDto shopDto = shopRepository.findByName(stock.getShop().getName())
                    .map(s -> modelMapper.fromShopToShopDto(s))
                    .orElseThrow(NullPointerException::new);

            StockDto stockDto = StockDto.builder()
                    .quantity(stock.getQuantity())
                    .productDto(productDto)
                    .shopDto(shopDto)
                    .build();

            stockDtoList.add(stockDto);
        }
        stockFinalList = stockDtoList.stream().map(modelMapper::fromStockDtoToStock)
                .collect(Collectors.toList());

        return stockFinalList;
    }
}
