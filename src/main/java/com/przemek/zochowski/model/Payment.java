package com.przemek.zochowski.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Payment {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private EPayment payment;
    @OneToMany (cascade = CascadeType.PERSIST, mappedBy = "payment")
    private Set<CustomerOrder> customerOrders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment1 = (Payment) o;
        return Objects.equals(id, payment1.id) &&
                payment == payment1.payment;
                //&& Objects.equals(customerOrders, payment1.customerOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payment);
    }

    @Override
    public String toString() {
        return id + ". " + payment;
    }
}
