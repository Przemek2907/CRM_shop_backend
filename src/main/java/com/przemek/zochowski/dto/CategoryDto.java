package com.przemek.zochowski.dto;


import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;


@Data
public class CategoryDto {

    private Long id;
    private String name;

    public CategoryDto(CategoryDtoBuilder categoryDtoBuilder){
        this.id = categoryDtoBuilder.id;
        this.name = categoryDtoBuilder.name;
    }

    public static CategoryDtoBuilder builder(){
        return new CategoryDtoBuilder();
    }


    public static class CategoryDtoBuilder {
        private Long id;
        private String name;


        public CategoryDtoBuilder id(Long id) {
            try {
                if (id == null) {
                    throw new NullPointerException("ID IS NULL");
                }
                this.id = id;
                return this;
            } catch (Exception e) {
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public CategoryDtoBuilder name(String name) {
            try {
                if (name == null) {
                    throw new NullPointerException("NAME IS NULL");
                }

                if (!name.matches("[A-Z ]+")) {
                    throw new IllegalArgumentException("NAME IS NOT VALID");
                }

                this.name = name;
                return this;
            } catch (Exception e) {
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }


        public CategoryDto build() {
            return new CategoryDto(this);
        }
    }
}



