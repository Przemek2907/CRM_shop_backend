package com.przemek.zochowski.service.dataFromFile;

import com.przemek.zochowski.dto.CategoryDto;
import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.ProducerDto;
import com.przemek.zochowski.dto.ProductDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.EGuarantee;
import com.przemek.zochowski.model.Product;
import com.przemek.zochowski.repository.CategoryRepository;
import com.przemek.zochowski.repository.CategoryRepositoryImpl;
import com.przemek.zochowski.repository.ProducerRepository;
import com.przemek.zochowski.repository.ProducerRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;


public class ProductDtoParser implements Parser<ProductDto> {

    CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    ProducerRepository producerRepository = new ProducerRepositoryImpl();
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public ProductDto parse(String text) {
        try{
                if (text == null){
                    throw new NullPointerException("NO DATA HAS BEEN FOUND FOR PRODUCT");
                } else if (!text.matches("[A-Z ]+;([0-9]+.[0-9]{2});[A-Z ]+;[A-Z ]+;\\w+")){
                    throw new IllegalArgumentException("WRONG DATA FORMAT FOR PRODUCT TABLE");
                }
                String arr[] = text.split(";");
            CategoryDto categoryDto = categoryRepository.findByName(arr[2])
                    .map(c -> modelMapper.fromCatagoryToCategoryDto(c))
                    .orElseThrow(NullPointerException::new);
            ProducerDto producerDto = producerRepository.findByName(arr[3])
                    .map(c-> modelMapper.fromProducerToProducerDto(c))
                    .orElseThrow(NullPointerException::new);


            return ProductDto.builder().name(arr[0]).price(BigDecimal.valueOf(Double.parseDouble(arr[1]))).categoryDto(categoryDto)
                    .producerDto(producerDto).guarantees(new HashSet<>(Arrays.asList(EGuarantee.valueOf(arr[4])))).build();
        } catch (Exception e){
            throw new MyException(ErrorCode.FILE_PARSER, e.getMessage());
        }
    }
}

