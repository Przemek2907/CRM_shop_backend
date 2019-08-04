package com.przemek.zochowski.dto;


import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class CustomerDto {
    private Long id;
    private String name;
    private String surname;
    private int age;
    private CountryDto countryDto;

    public CustomerDto(CustomerDtoBuilder customerDtoBuilder) {
        this.id = customerDtoBuilder.id;
        this.name = customerDtoBuilder.name;
        this.surname = customerDtoBuilder.surname;
        this.age = customerDtoBuilder.age;
        this.countryDto = customerDtoBuilder.countryDto;
    }

    public static CustomerDtoBuilder builder() {
        return new CustomerDtoBuilder();
    }



    public static class CustomerDtoBuilder {
        private Long id;
        private String name;
        private String surname;
        private int age;
        private CountryDto countryDto;

        public CustomerDtoBuilder id(Long id) {
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

        public CustomerDtoBuilder name(String name) {
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

        public CustomerDtoBuilder surname(String surname) {
            try {
                if (surname == null) {
                    throw new NullPointerException("SURNAME IS NULL");
                }

                if (!surname.matches("[A-Z ]+")) {
                    throw new IllegalArgumentException("SURNAME IS NOT VALID");
                }

                this.surname = surname;
                return this;
            } catch (Exception e) {
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public CustomerDtoBuilder age(int age) {
            try {
                if (age < 18) {
                    throw new IllegalArgumentException("AGE IS TOO LOW");
                }
                this.age = age;
                return this;
            } catch (Exception e) {
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public CustomerDtoBuilder countryDTO(CountryDto countryDto) {
            try {
                if (countryDto == null) {
                    throw new NullPointerException("COUNTRY IS NULL");
                }

                this.countryDto = countryDto;
                return this;
            } catch (Exception e) {
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public CustomerDto build() {
            return new CustomerDto(this);
        }
    }
}
