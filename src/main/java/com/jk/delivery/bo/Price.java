package com.jk.delivery.bo;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * Represents prices of delivery
 *
 * */
public class Price implements Comparable<Price> {

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer=10, fraction=3)
    private BigDecimal weight;

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer=7, fraction=2)
    private BigDecimal price;

    public Price(BigDecimal weight, BigDecimal price) {
        this.weight = weight;
        this.price = price;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public int compareTo(Price o) {
        return this.weight.compareTo(o.getWeight());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return weight.equals(price1.weight) && price.equals(price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, price);
    }

    @Override
    public String toString() {
        return "Price{" +
                "weight=" + weight +
                ", price=" + price +
                '}';
    }
}
