package com.przemek.zochowski.kmservice;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.StockDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.*;
import com.przemek.zochowski.repository.*;
import com.przemek.zochowski.service.DataManager;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class StockService {

    ModelMapper modelMapper = new ModelMapper();
    ProductRepository productRepository = new ProductRepositoryImpl();
    ShopRepository shopRepository = new ShopRepositoryImpl();
    StockRepository stockRepository = new StockRepositoryImpl();
    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();

    // TO DO LIST:
    // 1) ALLOW TO CREATE A NEW POSITION IN THE STOCK TABLE, BY:
            // a) TAKING FROM USER: NAME OF THE PRODUCT, SHOP NAME, COUNTRY LOCATION OF THE SHOP, QUANTITY

    // 2) ADD A METHOD CHECKING IF ADDED STOCK ITEM IS CURRENTLY IN THE GIVEN SHOP AND GIVEN COUNTRY..AND IF IT IS, UPDATE THE EXISTING RECORD IN THE TABLE




    public void addNewStockItem(DataManager dataManager) {
        try {

           if (checkingIfProductIsAlreadyInStockInTheShop(dataManager).isPresent()) {
               Stock stockItemBeingUpdated = checkingIfProductIsAlreadyInStockInTheShop(dataManager).get();
               int currentAmountInStock = stockItemBeingUpdated.getQuantity();
               stockItemBeingUpdated.setQuantity(currentAmountInStock+ dataManager.getQuantity());
               stockRepository.addOrUpdate(stockItemBeingUpdated);
            } else {

               Stock stockItem = modelMapper.fromStockDtoToStock(StockDto.builder()
                       .quantity(dataManager.getQuantity())
                       .build());

               Product product = productRepository
                       .findByName(dataManager.getProductName())
                       //orElse(modelMapper.fromCountryDtoToCountry(CountryDto.builder().name(dataManager.getStr3()).build()));
                       .orElseThrow(() -> new NullPointerException("PRODUCT DOES NOT EXIST. YOU  HAVE TO CREATE A PRODUCT FIRST"));

               Shop shop = shopRepository
                       .findByNameAndCountry(dataManager.getShopName(), dataManager.getCountryName())
                       .orElseThrow(() -> new NullPointerException("THERE IS NO SUCH SHOP IN THE GIVEN COUNTRY"));

               stockItem.setProduct(product);
               stockItem.setShop(shop);
               stockRepository.addOrUpdate(stockItem);
           }

        } catch (Exception e) {
            throw new MyException(ErrorCode.SERVICE, e.getMessage());
        }
    }

    private Optional<Stock> checkingIfProductIsAlreadyInStockInTheShop (DataManager dataManager) {
                Optional<Stock> stockItem = Optional.empty();
        try {
            stockItem = stockRepository.findAll().stream()
                    .filter(s -> s.getProduct().getName().equals(dataManager.getProductName()))
                    .filter(s -> s.getShop().getName().equals(dataManager.getShopName()))
                    .filter(s-> s.getShop().getCountry().getName().equals(dataManager.getCountryName()))
                    .limit(1)
                    .findFirst();

        } catch (Exception e) {
            throw new MyException(ErrorCode.SERVICE, e.getMessage());
        }
       return stockItem;
    }

    public List<DataManager> presentingTheListOfProductsWithStockAmount(){
        List<DataManager> listOfItemsInStock = stockRepository.findAll()
                .stream()
                .map(stock -> DataManager.builder()
                        .theIdOfTheSelectedProduct(stock.getProduct().getId())
                        .productName(stock.getProduct().getName())
                        .shopName(stock.getShop().getName())
                        .quantity(stock.getQuantity())
                        .price(stock.getProduct().getPrice())
                        .build())
                .collect(Collectors.toList());
        return listOfItemsInStock;
    }

    public boolean checkingIfProductExistsInDifferentShops (DataManager dataManager){
        long productsInStockStats = stockRepository.findAll()
                .stream()
                .filter(stock -> stock.getProduct().getId().equals(dataManager.getTheIdOfTheSelectedProduct()))
                .count();

        return productsInStockStats > 1 ? true : false;
    }
}
