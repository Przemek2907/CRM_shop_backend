package com.przemek.zochowski.services.dataFromFile.converters;


import com.przemek.zochowski.services.dataFromFile.ProducerJson;

public class ProducerJsonConverter extends JsonConverter<ProducerJson> {

    public ProducerJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
