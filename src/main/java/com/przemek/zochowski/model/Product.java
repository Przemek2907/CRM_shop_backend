package com.przemek.zochowski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    @Pattern(regexp = "([A-Z]+\\s*)*")
    private String name;
    private BigDecimal price;
    @OneToMany ( cascade = CascadeType.PERSIST, mappedBy = "product")
    private Set<Stock> stocks;
    @OneToMany (cascade = CascadeType.PERSIST, mappedBy = "product")
    private Set<CustomerOrder> customerOrders;
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "producer_id")
    private Producer producer;
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "category_id")
    private Category category;
    @ElementCollection
    @CollectionTable (
            name = "guarantee_components",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "guarantee_component")
    @Enumerated(EnumType.STRING)
    private Set<EGuarantee> guarantees;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                //", stocks=" + stocks +
               // ", customerOrders=" + customerOrders +
               /* ", producer=" + producer +
               ", category=" + category +*/
                ", guarantees=" + guarantees +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
               // Objects.equals(stocks, product.stocks) &&
                //Objects.equals(customerOrders, product.customerOrders) &&
              //Objects.equals(producer, product.producer) &&
                //Objects.equals(category, product.category) &&
               Objects.equals(guarantees, product.guarantees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, guarantees);
    }
}
