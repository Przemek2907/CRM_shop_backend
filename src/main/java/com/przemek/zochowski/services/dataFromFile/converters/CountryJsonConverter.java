package com.przemek.zochowski.services.dataFromFile.converters;


import com.przemek.zochowski.services.dataFromFile.CountryJson;

public class CountryJsonConverter extends JsonConverter<CountryJson> {

    public CountryJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
