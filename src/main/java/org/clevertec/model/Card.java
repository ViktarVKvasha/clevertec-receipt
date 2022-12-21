package org.clevertec.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Класс скидочаня карта
 *
 * @author Viktar Kvasha
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Card implements Serializable {

    private int id;
    private double discount;

    @Builder
    public Card(int id, double discount) {
        this.id = id;
        this.discount = discount;
    }
}
