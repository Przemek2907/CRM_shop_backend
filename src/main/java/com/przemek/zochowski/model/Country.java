package com.przemek.zochowski.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Country {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany (cascade = CascadeType.PERSIST  , mappedBy = "country")
    private Set<Customer> customers;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany (cascade = CascadeType.PERSIST  ,mappedBy = "country")
    private Set<Shop> shops;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany (cascade = CascadeType.PERSIST, mappedBy = "country")
    private Set<Producer> producers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(name, country.name); //&&
               /* Objects.equals(customers, country.customers) &&
                Objects.equals(shops, country.shops) ;*/
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name;
    }
}
