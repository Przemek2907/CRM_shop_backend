package com.przemek.zochowski.dto;


import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDto that = (CategoryDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
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



