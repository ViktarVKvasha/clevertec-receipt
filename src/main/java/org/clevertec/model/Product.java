package org.clevertec.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Класс продукт
 *
 * @author Viktar Kvasha
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Product implements Serializable {

    private int id;
    private String description;
    private BigDecimal price;
    private double discount;

    @Builder
    public Product(int id, String description, BigDecimal price, double discount) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.discount = discount;
    }
}
