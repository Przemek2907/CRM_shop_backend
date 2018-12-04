package com.przemek.zochowski.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private int age;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany (cascade = CascadeType.PERSIST, mappedBy = "customer")
    private Set<CustomerOrder> orders;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country_id")
    private Country country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return age == customer.age &&
                Objects.equals(id, customer.id) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(surname, customer.surname);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, age);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age;

    }
}
