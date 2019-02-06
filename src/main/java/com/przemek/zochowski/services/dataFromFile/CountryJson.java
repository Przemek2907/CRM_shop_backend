package com.przemek.zochowski.services.dataFromFile;

import com.przemek.zochowski.dto.CountryDto;
import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.model.Country;
import com.przemek.zochowski.repository.CountryRepository;
import com.przemek.zochowski.repository.CountryRepositoryImpl;
import com.przemek.zochowski.services.dataFromFile.converters.CountryJsonConverter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class CountryJson {
    private Set<CountryDto> countriesDto;
    ModelMapper modelMapper = new ModelMapper();
    CountryRepository countryRepository = new CountryRepositoryImpl();


    public List<Country> createListOfUniqueCountriesforDB () {
        List<Country> countryListToInsert = new ArrayList<>();
        try {
            countryListToInsert =  new CountryJsonConverter(Filenames.COUNTRY_JSON)
                    .fromJson().orElseThrow(IllegalStateException::new)
                    .getCountriesDto()
                    .stream()
                    .map(modelMapper::fromCountryDtoToCountry)
                    .collect(Collectors.toList());

            compareTheListWithTheDB(countryListToInsert);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countryListToInsert;
    }

    private void compareTheListWithTheDB (List<Country> countriesToBePutToDB){
        List<Country> countriesInTheDB = countryRepository.findAll();
        System.out.println(countriesInTheDB);
        for (Country country: countriesInTheDB) {
            countriesToBePutToDB.removeIf(c -> c.getName().equals(country.getName()));
        }
        System.out.println(countriesToBePutToDB);
    }
}
