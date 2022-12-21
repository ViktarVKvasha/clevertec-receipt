package org.clevertec.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Класс чек
 *
 * @author Viktar Kvasha
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Receipt implements Serializable {

    private int id;
    private String company;
    private LocalDate date;
    private LocalTime time;
    private Map<Product, Integer> products;
    private Map<Integer, ProxyCashReport> proxyCashReports;
    private Card discountCard;
    private BigDecimal totalPrice;
    private BigDecimal totalDiscount;
    private BigDecimal finalPrice;

    @Builder
    public Receipt(int id, String company, LocalDate date, LocalTime time,
                   Map<Product, Integer> products,
                   Map<Integer, ProxyCashReport> proxyCashReports, Card discountCard,
                   BigDecimal totalPrice, BigDecimal totalDiscount, BigDecimal finalPrice) {
        this.id = id;
        this.company = company;
        this.date = date;
        this.time = time;
        this.products = products;
        this.proxyCashReports = proxyCashReports;
        this.discountCard = discountCard;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.finalPrice = finalPrice;
    }
}
