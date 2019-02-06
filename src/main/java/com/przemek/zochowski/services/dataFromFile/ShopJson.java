package com.przemek.zochowski.services.dataFromFile;

import com.przemek.zochowski.dto.CountryDto;
import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.ShopDto;
import com.przemek.zochowski.model.Shop;
import com.przemek.zochowski.repository.CountryRepository;
import com.przemek.zochowski.repository.CountryRepositoryImpl;
import com.przemek.zochowski.repository.ShopRepository;
import com.przemek.zochowski.repository.ShopRepositoryImpl;
import com.przemek.zochowski.services.dataFromFile.converters.ShopJsonConverter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class ShopJson {
    private Set<ShopDto> shopsDto;
    ModelMapper modelMapper = new ModelMapper();
    ShopRepository shopRepository = new ShopRepositoryImpl();
    CountryRepository countryRepository = new CountryRepositoryImpl();

    public List<Shop> createListOfUniqueShopssForDB (){
        List<Shop> shopListToInsert = new ArrayList<>();
        try{
            shopListToInsert = new ShopJsonConverter(Filenames.SHOP_JSON)
                    .fromJson()
                    .orElseThrow(IllegalAccessException::new)
                    .getShopsDto()
                    .stream()
                    .map(modelMapper::fromShopDtoToShop)
                    .collect(Collectors.toList());

            compareTheListWithTheDB(shopListToInsert);

        }catch (Exception e){
            e.printStackTrace();
        }
        return shopListToInsert;
    }

    private void compareTheListWithTheDB (List<Shop> shopsToBePutToDB){
        List<Shop> shopsInTheDB = shopRepository.findAll();
        for (Shop shop: shopsInTheDB){
            shopsToBePutToDB.removeIf(c -> c.getName().equals(shop.getName())
                    && c.getCountry().getName().equals(shop.getCountry().getName()));
        }
    }

    public List<Shop> addShopWithRelatedCountry (List<Shop> shopsToBePutToDB){
        List<ShopDto> shopDtoList = new ArrayList<>();
        List<Shop> shopsFinalListToDB = new ArrayList<>();

        for (Shop shop : shopsToBePutToDB){
            CountryDto countryDto = countryRepository.findByName(shop.getCountry().getName())
                    .map(c -> modelMapper.fromCountryToCountryDto(c))
                    .orElseThrow(NullPointerException::new);

            ShopDto shopDto = ShopDto.builder().name(shop.getName()).countryDto(countryDto).build();

            shopDtoList.add(shopDto);
        }

        shopsFinalListToDB = shopDtoList.stream().map(modelMapper::fromShopDtoToShop)
                .collect(Collectors.toList());

        return shopsFinalListToDB;
    }
}
