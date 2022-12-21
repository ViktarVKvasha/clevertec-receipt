package org.clevertec.services;

import java.math.BigDecimal;
import java.util.Map;

import org.clevertec.model.Card;
import org.clevertec.model.Product;
import org.clevertec.model.ProxyCashReport;

/**
 * @author Viktar Kvasha
 */

public interface ArithmeticKernel {

    /**
     * Подсчет полной стоймости позиции продукта
     *
     * @param product продукт
     * @param quantity кол-во продукта
     * @return полная стоймость позиции
     */
    BigDecimal calculatePositionPrice(final Product product, int quantity);

    /**
     * Подсчет полной скидки позиции продукта
     *
     * @param product продукт
     * @param quantity кол-во продукта
     * @param card скидочная карта
     * @return полная скидка по позиции
     */
    BigDecimal calculatePositionDiscount(final Product product, int quantity, final Card card);

    /**
     * Подсчет промежуточных отчетов
     *
     * @param products продукты
     * @param card скидочная карта
     * @return финансы
     */
    Map<Integer, ProxyCashReport> calculateProxyCashReports(final Map<Product, Integer> products,
                                                            final Card card);

    /**
     * Подсчет полной стоймости продуктов
     *
     * @param products список продуктов
     * @return полная стоймость продутов
     */
    BigDecimal calculateTotalPrice(final Map<Product, Integer> products);

    /**
     * Подсчет полной скидки продуктов
     *
     * @param products список продуктов
     * @param card скидочаня карта
     * @return полная скидка продуктов
     */
    BigDecimal calculateTotalDiscount(final Map<Product, Integer> products, final Card card);
}
