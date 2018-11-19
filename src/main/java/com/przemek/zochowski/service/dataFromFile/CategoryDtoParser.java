package com.przemek.zochowski.service.dataFromFile;

import com.przemek.zochowski.dto.CategoryDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Category;

import java.time.LocalDateTime;

public class CategoryDtoParser implements Parser<CategoryDto> {

    @Override
    public CategoryDto parse(String text) {
        try {
            if (text == null) {
                throw new NullPointerException("NO DATA HAS BEEN FOUND FOR THE CATEGORY TABLE");
            } else if (!text.matches("([A-Z]+\\s*)*")) {
                throw new IllegalArgumentException("WRONG CATEGORY DATA FORMAT");
            }
            return CategoryDto.builder().name(text).build();
        } catch (Exception e) {
                throw new MyException(ErrorCode.FILE_PARSER, e.getMessage());
        }
    }
}
