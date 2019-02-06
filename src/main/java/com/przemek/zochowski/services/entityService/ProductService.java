package com.przemek.zochowski.services.entityService;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.ProductDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.*;
import com.przemek.zochowski.services.dataInputByUser.DataManager;
import com.przemek.zochowski.repository.*;

import java.util.*;
import java.util.stream.Collectors;

public class ProductService {

    ModelMapper modelMapper = new ModelMapper();
    ProducerRepository producerRepository = new ProducerRepositoryImpl();
    ProductRepository productRepository = new ProductRepositoryImpl();
    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
    CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    ShopRepository shopRepository = new ShopRepositoryImpl();

    // TO DO LIST:
    // 1) CREATE A METHOD TO ADD A NEW PRODUCT (PRODUCT NAME, PRICE, CATEGORY NAME, PRODUCER NAME (IF ONE DOESN'T EXIST, THEN WHAT? 2 OPTIONS:
    // A) CREATE A SUPPLEMENTARY METHOD TO ADD ONE (THEN I NEED TO TAKE COUNTRY AS AN INPUT TOO);
    // B) INFORM THE USER THAT HE NEEDS TO ADD A PRODUCER FIRST, CAUSE THERE IS NO SUCH PRODUCER)
    // GUARANTEE SERVICE TYPE FROM THE ENUM LIST;


    // 2) CREATE A METHOD, CHECKING IF PROVIDED PRODUCT DOES ALREADY EXIST IN THE TABLE (CHECKED BASED ON PRODUCT NAME, CATEGORY AND PRODUCER NAME);


    public void addNewProduct(DataManager dataManager) {
        try {

            if (isProductInTheTable(dataManager)) {
                throw new IllegalArgumentException("PRODUCT OF THIS CATEGORY MADE BY THE GIVEN PRODUCENT ALREADY EXISTS");
            }

            Product product = modelMapper.fromProductDtoToProduct(ProductDto.builder()
                    .name(dataManager.getProductName())
                    .price(dataManager.getPrice())
                    .build());

            Producer producer = producerRepository
                    .findByName(dataManager.getProducerName())
                    //orElse(modelMapper.fromCountryDtoToCountry(CountryDto.builder().name(dataManager.getStr3()).build()));
                    .orElseThrow(() -> new NullPointerException("PRODUCER DOESN'T EXIST. YOU  HAVE TO CREATE A PRODUCER FIRST TO BE ABLE TO ASSOCIATE" +
                            "PRODUCTS WITH A PRODUCER"));

            Category category = categoryRepository
                    .findByName(dataManager.getCategoryName())
                    .orElseThrow(() -> new NullPointerException("THERE IS NO SUCH CATEGORY. MAKE SURE YOU CREATE IT FIRST"));

            EGuarantee guarantee = dataManager.getEGuarantee();


            product.setProducer(producer);
            product.setCategory(category);
            product.setGuarantees(new HashSet<>(Arrays.asList(guarantee)));
            productRepository.addOrUpdate(product);

        } catch (MyException e) {
            throw new MyException(ErrorCode.SERVICE, e.getMessage());
        }
    }


    public boolean isProductInTheTable(DataManager dataManager) {
        try {
            if (dataManager.getProductName() == null) {
                throw new NullPointerException("PRODUCT NAME IS NULL");
            }

            if (dataManager.getProducerName() == null) {
                throw new NullPointerException("PRODUCER NAME IS NULL");
            }

            if (dataManager.getCategoryName() == null) {
                throw new NullPointerException("CATEGORY IS NULL");
            }

            Product product = productRepository
                    .findByName(dataManager.getProductName())
                    .orElse(null);

            if (product == null) {
                return false;
            }

            Producer producer = producerRepository
                    .findByName(dataManager.getProducerName())
                    .orElseThrow(() -> new NullPointerException("THIS PRODUCER HAS NOT BEEN FOUND IN THE DATABASE"));

            Category category = categoryRepository
                    .findByName(dataManager.getCategoryName())
                    .orElseThrow(() -> new NullPointerException("THIS CATEGORY HAS NOT BEEN FOUND IN THE DATABASE"));

            List<Product> products = productRepository.findAll()
                    .stream()
                    .filter(p -> p.getProducer().getId().equals(producer.getId()))
                    .filter(p -> p.getCategory().getId().equals(category.getId()))
                    .collect(Collectors.toList());

            return products.contains(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.SERVICE, e.getMessage());

        }
    }
}


