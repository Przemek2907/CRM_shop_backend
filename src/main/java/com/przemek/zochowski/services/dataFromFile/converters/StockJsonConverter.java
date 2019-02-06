package com.przemek.zochowski.services.dataFromFile.converters;


import com.przemek.zochowski.services.dataFromFile.StockJson;

public class StockJsonConverter extends JsonConverter<StockJson> {

    public StockJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
