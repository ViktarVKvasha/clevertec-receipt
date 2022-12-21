package org.clevertec.services.impl;

import org.clevertec.constants.Constants;
import org.clevertec.model.Card;
import org.clevertec.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

class BaseArithmeticKernelTest {
    private static final BigDecimal price = new BigDecimal("21.70").round(MathContext.DECIMAL64);
    private static final double discount = 10.00;
    private static final double cardDiscount = 7.00;
    private static final double zeroDiscount = 0.00;
    private static final int productQuantity = Constants.PROMOTION_QUANTITY - 1;
    private static final int productQuantityOnDiscount = Constants.PROMOTION_QUANTITY + 1;
    private static final Product productWithDiscount = Product.builder()
            .id(1).description("product1")
            .price(price).discount(discount).build();
    private static final Product productWithNoDiscount = Product.builder()
            .id(2).description("product2")
            .price(price).discount(zeroDiscount).build();

    private static final Card card = Card.builder().id(1).discount(cardDiscount).build();

    private BaseArithmeticKernel baseArithmeticKernel;

    @BeforeEach
    public void beforeEach() {
        baseArithmeticKernel = new BaseArithmeticKernel();
    }

    @Test
    void calculatePositionPrice() {
        Assertions.assertEquals(0,
                baseArithmeticKernel.calculatePositionPrice(productWithNoDiscount, productQuantity)
                        .compareTo(
                            price.multiply(BigDecimal.valueOf(productQuantity))
                            .round(MathContext.DECIMAL64)), "Должно вернуть 0 если результаты совпадают.");
    }

    @Test
    void calculatePositionDiscountWithCardDiscount() {
        Assertions.assertEquals(0, baseArithmeticKernel.calculatePositionDiscount(productWithNoDiscount, productQuantity, card)
                .compareTo(
                    price.multiply(BigDecimal.valueOf(productQuantity))
                    .multiply(BigDecimal.valueOf(cardDiscount))
                    .divide(BigDecimal.valueOf(100.00), MathContext.DECIMAL64)
                    .round(MathContext.DECIMAL64)), "Должно вернуть 0 если результаты совпадают.");
    }

    @Test
    void calculatePositionDiscountWithProductDiscount() {
        Assertions.assertEquals(0, baseArithmeticKernel.calculatePositionDiscount(productWithDiscount, productQuantityOnDiscount, null)
            .compareTo(
                price.multiply(BigDecimal.valueOf(productQuantityOnDiscount))
                    .multiply(BigDecimal.valueOf(discount))
                    .divide(BigDecimal.valueOf(100.00), MathContext.DECIMAL64)
                    .round(MathContext.DECIMAL64)), "Должно вернуть 0 если результаты совпадают.");
    }

    @Test
    void calculatePositionDiscountWithProductDiscountButNotEnoughQuantity() {
        Assertions.assertEquals(0, baseArithmeticKernel.calculatePositionDiscount(productWithDiscount, productQuantity, null)
            .compareTo(
                price.multiply(BigDecimal.valueOf(productQuantity))
                    .multiply(BigDecimal.valueOf(zeroDiscount))
                    .divide(BigDecimal.valueOf(100.00), MathContext.DECIMAL64)
                    .round(MathContext.DECIMAL64)), "Должно вернуть 0 если результаты совпадают.");
    }

    @Test
    void calculatePositionDiscountWithNoCardDiscountWithNoProductDiscount() {
        Assertions.assertEquals(0, baseArithmeticKernel.calculatePositionDiscount(productWithNoDiscount, productQuantity, null)
                .compareTo(
                    price.multiply(BigDecimal.valueOf(productQuantity))
                    .multiply(BigDecimal.valueOf(zeroDiscount))
                    .divide(BigDecimal.valueOf(100.00), MathContext.DECIMAL64)
                    .round(MathContext.DECIMAL64)), "Должно вернуть 0 если результаты совпадают.");
    }

    @Test
    void calculatePositionDiscountWithCardDiscountAndProductDiscount() {
        Assertions.assertEquals(0, baseArithmeticKernel.calculatePositionDiscount(productWithDiscount, productQuantityOnDiscount, card)
                .compareTo(
                    price.multiply(BigDecimal.valueOf(productQuantityOnDiscount))
                    .multiply(BigDecimal.valueOf(discount))
                    .divide(BigDecimal.valueOf(100.00), MathContext.DECIMAL64)
                    .round(MathContext.DECIMAL64)), "Должно вернуть 0 если результаты совпадают.");
    }

    @Test
    void calculatePositionDiscountWithCardDiscountAndProductDiscountButNotEnoughQuantity() {
        Assertions.assertEquals(0, baseArithmeticKernel.calculatePositionDiscount(productWithDiscount, productQuantity, card)
                .compareTo(
                    price.multiply(BigDecimal.valueOf(productQuantity))
                    .multiply(BigDecimal.valueOf(cardDiscount))
                    .divide(BigDecimal.valueOf(100.00), MathContext.DECIMAL64)
                    .round(MathContext.DECIMAL64)), "Должно вернуть 0 если результаты совпадают.");
    }
}