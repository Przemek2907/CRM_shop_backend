package com.przemek.zochowski.services.dataFromFile.converters;

import com.przemek.zochowski.services.dataFromFile.CategoryJson;

public class CategoryJsonConverter extends JsonConverter<CategoryJson> {
    public CategoryJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
