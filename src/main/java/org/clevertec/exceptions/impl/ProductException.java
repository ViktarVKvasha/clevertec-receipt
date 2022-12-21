package org.clevertec.exceptions.impl;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Класс исключения для продукта
 *
 * @author Viktar Kvasha
 */

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
public class ProductException extends RuntimeException {

    private String model;

    @Builder
    public ProductException(String message, String model) {
        super(message);
        this.model = model;
    }
}
