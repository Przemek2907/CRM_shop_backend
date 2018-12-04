package com.przemek.zochowski.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Stock {

    @Id
    @GeneratedValue
    private Long id;
    private int quantity;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "shop_id")
    private Shop shop;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "product_id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return quantity == stock.quantity &&
                Objects.equals(id, stock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", shop=" + shop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
