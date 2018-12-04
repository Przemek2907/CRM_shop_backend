package com.przemek.zochowski.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Trade {

    @Id
    @GeneratedValue
    private Long id;
    private String industry;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "trade")
    private Set<Producer> producers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return Objects.equals(id, trade.id) &&
                Objects.equals(industry, trade.industry);
        //&&
        //Objects.equals(producers, trade.producers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, industry);
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", industry='" + industry;
    }
}
