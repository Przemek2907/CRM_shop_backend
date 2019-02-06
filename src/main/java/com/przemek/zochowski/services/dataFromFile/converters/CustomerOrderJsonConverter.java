package com.przemek.zochowski.services.dataFromFile.converters;

import com.przemek.zochowski.services.dataFromFile.CustomerOrderJson;

public class CustomerOrderJsonConverter extends JsonConverter<CustomerOrderJson> {

    public CustomerOrderJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
