package com.przemek.zochowski.dto;


import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;

import java.time.LocalDateTime;
import java.util.Objects;

public class StockDto {

    private Long id;
    private int quantity;
    private ShopDto shopDto;
    private ProductDto productDto;

    public StockDto (StockDtoBuilder stockDtoBuilder){
        this.id = stockDtoBuilder.id;
        this.quantity = stockDtoBuilder.quantity;
        this.shopDto = stockDtoBuilder.shopDto;
        this.productDto = stockDtoBuilder.productDto;
    }

    public static StockDtoBuilder builder(){
        return new StockDtoBuilder();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ShopDto getShopDto() {
        return shopDto;
    }

    public void setShopDto(ShopDto shopDto) {
        this.shopDto = shopDto;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDto stockDto = (StockDto) o;
        return quantity == stockDto.quantity &&
                Objects.equals(id, stockDto.id) &&
                Objects.equals(shopDto, stockDto.shopDto) &&
                Objects.equals(productDto, stockDto.productDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, shopDto, productDto);
    }

    public static class StockDtoBuilder {
        private Long id;
        private int quantity;
        private ShopDto shopDto;
        private ProductDto productDto;

        public StockDtoBuilder id (Long id){
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

        public StockDtoBuilder quantity (int quantity){
            try{
                if (quantity<=0){
                    throw new IllegalArgumentException("QUANTITY HAS TO BE GREATER THAN 0");
                }
                this.quantity = quantity;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public StockDtoBuilder shopDto (ShopDto shopDto){
            try{
                if (shopDto == null){
                    throw new NullPointerException("NO DATA FOR SHOP HAS BEEN PROVIDED");
                }
                this.shopDto = shopDto;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }


        public StockDtoBuilder productDto (ProductDto productDto){
            try{
                if (productDto == null){
                    throw new NullPointerException("NO DATA FOR PRODUCT HAS BEEN PROVIDED");
                }
                this.productDto = productDto;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public StockDto build(){
            return new StockDto(this);
        }

    }
}
