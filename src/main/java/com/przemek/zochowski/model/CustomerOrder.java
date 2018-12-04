package com.przemek.zochowski.model;


import lombok.*;

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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "product_id")
    private Product product;
}
