package com.przemek.zochowski.dto;


import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;


public class CountryDto {

    private Long id;
    private String name;

    public CountryDto(CountryDtoBuilder countryDtoBuilder){
        this.id = countryDtoBuilder.id;
        this.name = countryDtoBuilder.name;
    }

    public static CountryDtoBuilder builder(){
        return new CountryDtoBuilder();
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
        CountryDto that = (CountryDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public static class CountryDtoBuilder {
        private Long id;
        private String name;


        public CountryDtoBuilder id(Long id) {
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

        public CountryDtoBuilder name(String name) {
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


        public CountryDto build() {
            return new CountryDto(this);
        }
    }
}
