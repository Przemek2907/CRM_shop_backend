package com.przemek.zochowski.services.dataFromFile;

import com.przemek.zochowski.dto.CategoryDto;
import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.ProducerDto;
import com.przemek.zochowski.dto.ProductDto;
import com.przemek.zochowski.model.Product;
import com.przemek.zochowski.repository.*;
import com.przemek.zochowski.services.dataFromFile.converters.ProductJsonConverter;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;


@Data
public class ProductJson {
    private Set<ProductDto> productsDto;
    ModelMapper modelMapper = new ModelMapper();
    ProductRepository productRepository = new ProductRepositoryImpl();
    CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    ProducerRepository producerRepository = new ProducerRepositoryImpl();


    public List<Product> createListOfUniqueProducts (){
        List<Product> productListToBePutInDB = new ArrayList<>();

        try{
            productListToBePutInDB = new ProductJsonConverter(Filenames.PRODUCT_JSON)
                    .fromJson()
                    .orElseThrow(IllegalStateException::new)
                    .getProductsDto()
                    .stream()
                    .map(modelMapper::fromProductDtoToProduct)
                    .collect(Collectors.toList());

            removeDuplicatesComparedToDB(productListToBePutInDB);
        }catch (Exception e){
            e.printStackTrace();
        }

        return productListToBePutInDB;
    }

    private void removeDuplicatesComparedToDB (List<Product> productList){
        List<Product> productListFromDB = productRepository.findAll();
        for (Product product: productListFromDB){
            productList.removeIf(p -> p.getName().equals(product.getName())
                    && p.getCategory().getName().equals(product.getCategory().getName())
                    && p.getProducer().getName().equals(product.getProducer().getName()));
        }
    }

    public List<Product> productListWithRelatedCategoryAndProducer (List<Product> productListToBePutInTheDB){
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> finalListToBePutInDB;
        for (Product product : productListToBePutInTheDB){
            CategoryDto categoryDto = categoryRepository.findByName(product.getCategory().getName())
                    .map(c -> modelMapper.fromCatagoryToCategoryDto(c))
                    .orElseThrow(NullPointerException::new);

            ProducerDto producerDto = producerRepository.findByName(product.getProducer().getName())
                    .map(p -> modelMapper.fromProducerToProducerDto(p))
                    .orElseThrow(NullPointerException::new);

            ProductDto productDto = ProductDto.builder().name(product.getName())
                    .price(product.getPrice())
                    .categoryDto(categoryDto)
                    .producerDto(producerDto)
                    .guarantees(product.getGuarantees())
                    .build();

            productDtos.add(productDto);
        }
        finalListToBePutInDB = productDtos.stream().map(modelMapper::fromProductDtoToProduct)
                .collect(Collectors.toList());

        return finalListToBePutInDB;
    }

}
