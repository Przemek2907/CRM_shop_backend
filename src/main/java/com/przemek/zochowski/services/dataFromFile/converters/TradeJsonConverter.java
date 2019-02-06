package com.przemek.zochowski.services.dataFromFile.converters;

import com.przemek.zochowski.services.dataFromFile.TradeJson;

public class TradeJsonConverter extends JsonConverter<TradeJson> {

    public TradeJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
