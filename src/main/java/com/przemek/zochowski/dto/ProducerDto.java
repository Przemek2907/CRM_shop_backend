package com.przemek.zochowski.dto;


import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class ProducerDto {

    private Long id;
    private String name;
    private CountryDto countryDto;
    private TradeDto tradeDto;

    public ProducerDto(ProducerBuilderDto producerBuilderDto){
        this.id = producerBuilderDto.id;
        this.name = producerBuilderDto.name;
        this.countryDto = producerBuilderDto.countryDto;
        this.tradeDto = producerBuilderDto.tradeDto;
    }

    public static ProducerBuilderDto builder () {
        return new ProducerBuilderDto();
    }

    public static class ProducerBuilderDto {
        private Long id;
        private String name;
        private CountryDto countryDto;
        private TradeDto tradeDto;

        public ProducerBuilderDto id(Long id) {
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

        public ProducerBuilderDto name(String name){
            try {
                if (name == null) {
                    throw new NullPointerException("NAME WAS NOT PROVIDED");
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

        public ProducerBuilderDto countryDto (CountryDto countryDto){
            try {
                if (countryDto == null) {
                    throw new NullPointerException("COUNTRY WAS NOT PROVIDED");
                }
                this.countryDto = countryDto;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public ProducerBuilderDto tradeDto (TradeDto tradeDto){
            try{
                if (tradeDto == null){
                    throw new NullPointerException("TRADE WAS NOT PROVIDED");
                }
                this.tradeDto = tradeDto;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public ProducerDto build (){
            return new ProducerDto(this);
        }
    }
}
