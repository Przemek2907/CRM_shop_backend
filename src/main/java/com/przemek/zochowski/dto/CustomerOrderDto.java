package com.przemek.zochowski.dto;



import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.zip.DataFormatException;

import static java.lang.String.format;

@Data
public class CustomerOrderDto {

    private Long id;
    private LocalDate date;
    private int quantity;
    private BigDecimal discount;
    private CustomerDto customerDto;
    private PaymentDto paymentDto;
    private ProductDto productDto;

    public CustomerOrderDto(CustomerOrderDtoBuilder customerOrderDtoBuilder){
        this.id = customerOrderDtoBuilder.id;
        this.date = customerOrderDtoBuilder.date;
        this.quantity = customerOrderDtoBuilder.quantity;
        this.discount = customerOrderDtoBuilder.discount;
        this.customerDto = customerOrderDtoBuilder.customerDto;
        this.paymentDto = customerOrderDtoBuilder.paymentDto;
        this.productDto = customerOrderDtoBuilder.productDto;
    }

    public static CustomerOrderDtoBuilder builder () {
        return new CustomerOrderDtoBuilder();
    }


  /*  public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public PaymentDto getPaymentDto() {
        return paymentDto;
    }

    public void setPaymentDto(PaymentDto paymentDto) {
        this.paymentDto = paymentDto;
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
        CustomerOrderDto that = (CustomerOrderDto) o;
        return quantity == that.quantity &&
                Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(customerDto, that.customerDto) &&
                Objects.equals(paymentDto, that.paymentDto) &&
                Objects.equals(productDto, that.productDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, quantity, discount, customerDto, paymentDto, productDto);
    }*/

    public static class CustomerOrderDtoBuilder {
        private Long id;
        private LocalDate date;
        private int quantity;
        private BigDecimal discount;
        private CustomerDto customerDto;
        private PaymentDto paymentDto;
        private ProductDto productDto;

        public CustomerOrderDtoBuilder id(Long id){
            try{
                if (id == null){
                    throw new NullPointerException("ID IS NULL");
                }
                this.id = id;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public CustomerOrderDtoBuilder date(LocalDate date) {
            try {
                if (date == null) {
                    throw new NullPointerException("DATE IS NULL");
                }

                /*if (!date.toString().matches("YYYY-MM-DD")) {
                    throw new DataFormatException("WRONG DATE FORMAT. DATE SHOULD BE PROVIDED IN A FORMAT ''.");
                }*/
                this.date = date;
                return this;
            } catch (Exception e) {
                e.printStackTrace();
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public  CustomerOrderDtoBuilder quantity(int quantity){
            try{
                if (quantity <= 0){
                    throw new NullPointerException("TO PLACE AN ORDER YOU HAVE TO ADD AT LEAST ONE ITEM TO THE CART");
                }
                this.quantity = quantity;
                return this;
            } catch (Exception e) {
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public CustomerOrderDtoBuilder discount(BigDecimal discount){
            try{
                if (discount.compareTo(BigDecimal.ZERO)<0 || discount.compareTo(BigDecimal.ONE) >1){
                    throw new IllegalArgumentException("DISCOUNT IS OUT OF RANGE");
                }
            this.discount = discount;
            return this;
        } catch (Exception e) {
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public CustomerOrderDtoBuilder customerDto (CustomerDto customerDto){
            try{
                if (customerDto == null) {
                    throw new NullPointerException("NO DATA FOR CUSTOMER");
                }
                this.customerDto = customerDto;
                return this;
        } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public CustomerOrderDtoBuilder paymentDto (PaymentDto paymentDto){
            try{
                if (paymentDto == null) {
                    throw new NullPointerException("NO DATA FOR PAYMENT");
                }
                this.paymentDto = paymentDto;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public CustomerOrderDtoBuilder productDto (ProductDto productDto){
            try{
                if (productDto == null) {
                    throw new NullPointerException("NO DATA FOR PRODUCT");
                }
                this.productDto = productDto;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public CustomerOrderDto build() {
            return new CustomerOrderDto(this);
        }
    }
}


