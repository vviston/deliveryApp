package com.jk.delivery.bo;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * Represents package delivery
 *
 * */
public class Delivery implements Comparable<Delivery> {

    private String UUID;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=3)
    private BigDecimal weight;

    @Pattern(regexp = "^[0-9]{5}")
    private String postalCode;

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer=7, fraction=2)
    private BigDecimal price;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return UUID.equals(delivery.UUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UUID);
    }

    @Override
    public int compareTo(Delivery o) {
        return this.weight.compareTo(o.getWeight());
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "UUID='" + UUID + '\'' +
                ", weight=" + weight +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
