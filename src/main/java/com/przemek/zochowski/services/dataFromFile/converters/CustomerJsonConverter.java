package com.przemek.zochowski.services.dataFromFile.converters;

import com.przemek.zochowski.services.dataFromFile.CustomerJson;

public class CustomerJsonConverter extends JsonConverter<CustomerJson> {

    public CustomerJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
