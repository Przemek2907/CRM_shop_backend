package com.przemek.zochowski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Producer {

    @Id
    @GeneratedValue
    private Long id;
    @Pattern(regexp = "([A-Z]+\\s*)*")
    private String name;
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "country_id")
    private Country country;
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "trade_id")
    private Trade trade;
    @OneToMany (cascade = CascadeType.PERSIST, mappedBy = "producer")
    private Set<Product> products;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(id, producer.id) &&
                Objects.equals(name, producer.name)// &&
               // Objects.equals(country, producer.country) ;//&&
                //Objects.equals(trade, producer.trade)
         &&
                Objects.equals(products, producer.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name,products);
    }


    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", name='" + name + products;
    }
}
