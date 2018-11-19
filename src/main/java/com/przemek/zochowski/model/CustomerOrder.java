package com.przemek.zochowski.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class CustomerOrder {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    private int quantity;
    private BigDecimal discount;
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "product_id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOrder that = (CustomerOrder) o;
        return quantity == that.quantity &&
                Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(discount, that.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, quantity, discount);
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + id +
                ", date=" + date +
                ", quantity=" + quantity +
                ", discount=" + discount;
    }
}
