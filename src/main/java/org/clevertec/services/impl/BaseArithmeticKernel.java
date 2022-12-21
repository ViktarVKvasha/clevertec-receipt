package org.clevertec.services.impl;

import org.clevertec.constants.Constants;
import org.clevertec.model.Card;
import org.clevertec.model.Product;
import org.clevertec.model.ProxyCashReport;
import org.clevertec.services.ArithmeticKernel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Реализация интерфейса ArithmeticKernel
 *
 * @author Viktar Kvasha
 * @see org.clevertec.services.ArithmeticKernel
 */

public class BaseArithmeticKernel implements ArithmeticKernel {

    public BaseArithmeticKernel() {
    }

    @Override
    public BigDecimal calculatePositionPrice(Product product, int quantity) {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity), MathContext.DECIMAL64);
    }

    @Override
    public BigDecimal calculatePositionDiscount(Product product, int quantity, Card card) {
        if (product.getDiscount() > 0 && quantity >= Constants.PROMOTION_QUANTITY) {
            return calculatePositionPrice(product, quantity).multiply(
                            BigDecimal.valueOf(product.getDiscount()), MathContext.DECIMAL64)
                    .divide(BigDecimal.valueOf(100.0), MathContext.DECIMAL64);
        } else if (card != null) {
            return calculatePositionPrice(product, quantity).multiply(
                            BigDecimal.valueOf(card.getDiscount()), MathContext.DECIMAL64)
                    .divide(BigDecimal.valueOf(100.0), MathContext.DECIMAL64);
        }
        return BigDecimal.valueOf(0);
    }

    @Override
    public Map<Integer, ProxyCashReport> calculateProxyCashReports(Map<Product, Integer> products,
                                                                   Card card) {
        Map<Integer, ProxyCashReport> finances = new LinkedHashMap<>();
        for (Product product : products.keySet()) {
            finances.put(product.getId(), ProxyCashReport.builder().price(calculatePositionPrice(product,
                            products.get(product)))
                    .discount(calculatePositionDiscount(product, products.get(product), card)).build());
        }
        return finances;
    }

    @Override
    public BigDecimal calculateTotalPrice(Map<Product, Integer> products) {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        if (!products.isEmpty()) {
            for (Product product : products.keySet()) {
                totalPrice = totalPrice.add(calculatePositionPrice(product, products.get(product)));
            }
        }
        return totalPrice;
    }

    @Override
    public BigDecimal calculateTotalDiscount(Map<Product, Integer> products, Card card) {
        BigDecimal totalDiscount = BigDecimal.valueOf(0);
        if (!products.isEmpty()) {
            for (Product product : products.keySet()) {
                totalDiscount = totalDiscount.add(
                        calculatePositionDiscount(product, products.get(product), card));
            }
        }
        return totalDiscount;
    }
}
