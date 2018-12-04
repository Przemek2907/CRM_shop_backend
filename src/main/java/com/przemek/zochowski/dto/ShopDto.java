package com.przemek.zochowski.dto;


import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class ShopDto {

    private Long id;
    private String name;
    private CountryDto countryDto;

    public ShopDto (ShopDtoBuilder shopDtoBuilder){
        this.id = shopDtoBuilder.id;
        this.name = shopDtoBuilder.name;
        this.countryDto = shopDtoBuilder.countryDto;
    }

    public static ShopDtoBuilder builder(){
        return new ShopDtoBuilder();
    }

  /*  public Long getId() {
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

    public CountryDto getCountryDto() {
        return countryDto;
    }

    public void setCountryDto(CountryDto countryDto) {
        this.countryDto = countryDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopDto shopDto = (ShopDto) o;
        return Objects.equals(id, shopDto.id) &&
                Objects.equals(name, shopDto.name) &&
                Objects.equals(countryDto, shopDto.countryDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryDto);
    }*/

    public static class ShopDtoBuilder {
        private Long id;
        private String name;
        private CountryDto countryDto;

        public ShopDtoBuilder id (Long id){
            try {
                if (id == null){
                    throw new NullPointerException("ID IS NULL");
                }
                this.id = id;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public ShopDtoBuilder name (String name){
            try{
                if (name == null){
                    throw new NullPointerException("NAME IS NULL");
                }

                if (!name.matches("[A-Z ]+")){
                    throw new IllegalArgumentException("NAME IS NOT VALID");
                }
                this.name = name;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public ShopDtoBuilder countryDto (CountryDto countryDto){
            try{
                if (countryDto == null){
                    throw new NullPointerException("COUNTRY HAS NOT BEEN PROVIDED");
                }
                this.countryDto = countryDto;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public ShopDto build (){
            return new ShopDto(this);
        }
    }
}
