package com.przemek.zochowski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
public class Shop {

    @Id
    @GeneratedValue
    private Long id;
    @Pattern(regexp = "([A-Z]+\\s*)*")
    private String name;
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "country_id")
    private Country country;
    @OneToMany (cascade = CascadeType.PERSIST, mappedBy = "shop")
    private Set<Stock> stocks;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return Objects.equals(id, shop.id) &&
                Objects.equals(name, shop.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name;
    }
}
