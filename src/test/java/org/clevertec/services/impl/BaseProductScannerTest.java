package org.clevertec.services.impl;

import org.clevertec.dao.Dao;
import org.clevertec.dao.impl.ProductDao;
import org.clevertec.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

class BaseProductScannerTest {
    private static final String PRODUCT_STRING_ONE = "1-2";
    private static final String PRODUCT_STRING_TWO = "2-3";
    private static final String PRODUCT_STRING_THREE = "3-4";
    private static final Optional<String[]> PRODUCT_MODEL_ONE = Optional.of(new String[]{"1", "2"});
    private static final Optional<String[]> PRODUCT_MODEL_TWO = Optional.of(new String[]{"2", "3"});
    private static final Optional<String[]> PRODUCT_MODEL_THREE = Optional.of(new String[]{"3", "4"});

    private static final Optional<Product> PRODUCT_ONE = Optional.of(Product.builder().id(1).description("product1")
        .price(new BigDecimal(10)).discount(10.0).build());

    private BaseProductScanner baseProductScanner;
    private static Dao<Product, Integer> productDao;
    private static ProductModelParser productParser;

    @BeforeAll
    static void setUp() throws IOException {
        productParser = Mockito.mock(ProductModelParser.class);
        productDao = Mockito.mock(ProductDao.class);
        Mockito.when(productParser.parse(PRODUCT_STRING_ONE)).thenReturn(PRODUCT_MODEL_ONE);
        Mockito.when(productParser.parse(PRODUCT_STRING_TWO)).thenReturn(PRODUCT_MODEL_TWO);
        Mockito.when(productParser.parse(PRODUCT_STRING_THREE)).thenReturn(PRODUCT_MODEL_THREE);
        Mockito.when(productDao.get(1)).thenReturn(PRODUCT_ONE);
        Mockito.when(productDao.get(2)).thenThrow(IOException.class);
    }

    @BeforeEach
    public void beforeEach() {
        baseProductScanner = new BaseProductScanner(productDao, productParser);
    }

    @Test
    void scanWhenNoData() throws IOException {
        Map<Product, Integer> products = baseProductScanner.scan(new String[]{});
        Assertions.assertTrue(products.isEmpty(), "Должен вернуть true, если на входе нету данных.");
    }

    @Test
    void scanWhenDataIsNotPresent() throws IOException {
        Map<Product, Integer> products = baseProductScanner.scan(new String[]{PRODUCT_STRING_ONE, ""});
        Assertions.assertEquals(Map.of(PRODUCT_ONE.get(), 2), products, "Должен вернуть Map<Product, Integer> с эквивалентной парой ключ-значение.");
    }

    @Test
    void scanWhenNoProductFound() throws IOException {
        Map<Product, Integer> products = baseProductScanner.scan(new String[]{PRODUCT_STRING_THREE});
        Assertions.assertTrue(products.isEmpty(), "Должен вернуть true, если не был найден продукт в источнике данных по данной моделе.");
    }

    @Test
    void scanWhenThrowException() {
        Assertions.assertThrows(IOException.class, () -> baseProductScanner.scan(new String[]{PRODUCT_STRING_TWO}),
            "Должно было упасть исключение, но его нет.");
    }
}