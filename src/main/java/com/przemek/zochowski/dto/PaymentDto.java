package com.przemek.zochowski.dto;


import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.EPayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;


public class PaymentDto {

    private Long id;
    private EPayment payment;

    public PaymentDto(PaymentDtoBuilder paymentDtoBuilder) {
        this.id = paymentDtoBuilder.id;
        this.payment = paymentDtoBuilder.payment;
    }

    public static PaymentDtoBuilder builder() {
        return new PaymentDtoBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EPayment getPayment() {
        return payment;
    }

    public void setPayment(EPayment payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDto that = (PaymentDto) o;
        return Objects.equals(id, that.id) &&
                payment == that.payment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payment);
    }

    public static class PaymentDtoBuilder {
        private Long id;
        private EPayment payment;


        public PaymentDtoBuilder id(Long id) {
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

        public PaymentDtoBuilder payment(EPayment payment) {
            try {
                if (payment == null) {
                    throw new NullPointerException("PAYMENT IS NULL");
                }
                this.payment = payment;
                return this;
            } catch (Exception e) {
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public PaymentDto build() {
            return new PaymentDto(this);

        }

    }
}

