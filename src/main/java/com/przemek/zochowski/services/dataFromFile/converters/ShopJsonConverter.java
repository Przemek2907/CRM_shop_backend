package com.przemek.zochowski.services.dataFromFile.converters;

import com.przemek.zochowski.services.dataFromFile.ShopJson;

public class ShopJsonConverter extends JsonConverter<ShopJson> {

    public ShopJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
