package com.przemek.zochowski.dto;


import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeDto tradeDto = (TradeDto) o;
        return Objects.equals(id, tradeDto.id) &&
                Objects.equals(industry, tradeDto.industry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, industry);
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
