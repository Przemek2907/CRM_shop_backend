package com.przemek.zochowski.service.dataFromFile;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.ProductDto;
import com.przemek.zochowski.dto.ShopDto;
import com.przemek.zochowski.dto.StockDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Stock;
import com.przemek.zochowski.repository.ProductRepository;
import com.przemek.zochowski.repository.ProductRepositoryImpl;
import com.przemek.zochowski.repository.ShopRepository;
import com.przemek.zochowski.repository.ShopRepositoryImpl;


import java.time.LocalDateTime;

public class StockDtoParser implements Parser<StockDto> {
    ProductRepository productRepository = new ProductRepositoryImpl();
    ShopRepository shopRepository = new ShopRepositoryImpl();

    ModelMapper modelMapper = new ModelMapper();
    @Override
    public StockDto parse(String text) {
        try{
            if (text == null){
                throw new NullPointerException("NO DATA FOUND FOR STOCK TABLE");
            } else if (!text.matches("([0-9]+;[A-Z ]+;[A-Z ]+)")){
                throw new IllegalArgumentException("WRONG DATA FORMAT FOR STOCK TABLE");
            }
            String arr[] = text.split(";");
            ProductDto productDto = productRepository.findByName(arr[1])
                    .map(p-> modelMapper.fromProductToProductDto(p))
                    .orElseThrow(NullPointerException::new);
            ShopDto shopDto = shopRepository.findByName(arr[2])
                    .map(s-> modelMapper.fromShopToShopDto(s))
                    .orElseThrow(NullPointerException::new);
            return StockDto.builder().quantity(Integer.parseInt(arr[0])).productDto(productDto).shopDto(shopDto).build();
        } catch (Exception e){
            throw new MyException(ErrorCode.FILE_PARSER, e.getMessage());
        }
    }
}
