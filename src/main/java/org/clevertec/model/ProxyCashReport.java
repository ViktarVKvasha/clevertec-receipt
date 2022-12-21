package org.clevertec.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Класс промежуточный отчет
 *
 * @author Viktar Kvasha
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class ProxyCashReport implements Serializable {

    private BigDecimal price;
    private BigDecimal discount;

    @Builder
    public ProxyCashReport(BigDecimal price, BigDecimal discount) {
        this.price = price;
        this.discount = discount;
    }
}
