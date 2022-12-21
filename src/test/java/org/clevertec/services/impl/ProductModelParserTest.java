package org.clevertec.services.impl;

import org.clevertec.exceptions.impl.ProductException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class ProductModelParserTest {
    private static final String PRODUCT_STRING_ONE = "card-123";
    private static final String PRODUCT_STRING_TWO = "1-4";
    private static final String PRODUCT_STRING_THREE = "1%2";
    private static final Optional<String[]> PRODUCT_MODEL_ONE = Optional.empty();
    private static final Optional<String[]> PRODUCT_MODEL_TWO = Optional.of(new String[]{"1", "4"});


    static ProductModelParser productModelParser;

    @BeforeAll
    static void setUp() {
        productModelParser = new ProductModelParser();
    }

    @Test
    void parseWhenStringContainsCard() {
        Optional<String[]> model = productModelParser.parse(PRODUCT_STRING_ONE);
        Assertions.assertEquals(Optional.empty(), PRODUCT_MODEL_ONE, "Должен вернуть Optional.empty() когда содержит card.");
    }

    @Test
    void parseWhenStringNotContainsCardAndHasValidData() {
        Optional<String[]> model = productModelParser.parse(PRODUCT_STRING_TWO);
        Assertions.assertArrayEquals(PRODUCT_MODEL_TWO.get(), model.get(), "Должен вернуть массив строк когда не содержит card и данные валидны.");
    }

    @Test
    void parseWhenStringNotContainsCardAndHasNotValidDataThrowProductException() {
        Assertions.assertThrows(ProductException.class, () -> productModelParser.parse(PRODUCT_STRING_THREE), "Должно было упасть исключение, но его нет.");
    }
}