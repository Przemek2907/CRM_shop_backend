package com.przemek.zochowski.dto;


import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;


@Data
public class TradeDto {

    private Long id;
    private String industry;

    public TradeDto (TradeDtoBuilder tradeDtoBuilder){
        this.id = tradeDtoBuilder.id;
        this.industry = tradeDtoBuilder.industry;
    }

    public static TradeDtoBuilder builder (){
        return new TradeDtoBuilder();
    }


    public static class TradeDtoBuilder {
        private Long id;
        private String industry;

        public TradeDtoBuilder id(Long id){
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

        public TradeDtoBuilder industry(String industry) {
            try {
                if (industry == null) {
                    throw new NullPointerException("INDUSTRY HAS NOT BEEN PROVIDED");
                }

                if (!industry.matches("[A-Z ]+")) {
                    throw new IllegalArgumentException("INDUSTRY NAME IS NOT VALID");
                }

                this.industry = industry;
                return this;
            } catch (Exception e) {
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public TradeDto build (){
            return new TradeDto(this);
        }
    }
}
