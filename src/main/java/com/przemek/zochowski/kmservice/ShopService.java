package com.przemek.zochowski.kmservice;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.ShopDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Country;
import com.przemek.zochowski.model.Customer;
import com.przemek.zochowski.model.Errors;
import com.przemek.zochowski.model.Shop;
import com.przemek.zochowski.repository.*;
import com.przemek.zochowski.service.DataManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ShopService {

    ShopRepository shopRepository = new ShopRepositoryImpl();
    CountryRepository countryRepository = new CountryRepositoryImpl();
    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
    ModelMapper modelMapper = new ModelMapper();


    public boolean isShopInTheSpecificCountry(DataManager dataManager) {
        try {
            if (dataManager.getShopName() == null) {
                throw new NullPointerException("SHOP NAME IS NULL");
            }

            if (dataManager.getCountryName() == null) {
                throw new NullPointerException("COUNTRY IS NULL");
            }

            Shop shop = shopRepository
                    .findByNameAndCountry(dataManager.getShopName(), dataManager.getCountryName())
                    .orElse(null);

            if (shop == null) {
                return false;
            }

            Country country = countryRepository
                    .findByName(dataManager.getCountryName())
                    .orElseThrow(() -> new NullPointerException("COUNTRY IS NULL"));

            List<Shop> shopList = shopRepository.findAll()
                    .stream()
                    .filter(c -> c.getCountry().getId().equals(country.getId()))
                    .collect(Collectors.toList());

            return shopList.contains(shop);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.SERVICE, e.getMessage());

        }
    }



    public void addShopWithCountry(DataManager dataManager) {
        try {

            if (isShopInTheSpecificCountry(dataManager)) {
                throw new IllegalArgumentException(" A SHOP WITH PROVIDED NAME ALREADY EXISTS IN GIVEN COUNTRY");
            }

            Shop shop = modelMapper.fromShopDtoToShop(
                    ShopDto.builder()
                            .name(dataManager.getShopName())
                            .build()
            );

            Country country = countryRepository
                    .findByName(dataManager.getCountryName())
                    .orElseThrow(() -> new NullPointerException("COUNTRY DOESN'T EXIST"));



            shop.setCountry(country);
            shopRepository.addOrUpdate(shop);

        } catch (Exception e) {
            throw new MyException(ErrorCode.SERVICE, e.getMessage());
        }
    }
}
