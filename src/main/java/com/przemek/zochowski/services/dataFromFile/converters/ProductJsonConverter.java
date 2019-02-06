package com.przemek.zochowski.services.dataFromFile.converters;


import com.przemek.zochowski.services.dataFromFile.ProductJson;

public class ProductJsonConverter extends JsonConverter<ProductJson> {

    public ProductJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
