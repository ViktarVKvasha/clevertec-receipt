package org.clevertec.services.impl;

import org.clevertec.exceptions.impl.CardException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class CardModelParseTest {
    private static final String CARD_STRING_ONE = "1-123";
    private static final String CARD_STRING_TWO = "card-4";
    private static final String CARD_STRING_THREE = "card2";
    private static final Optional<String[]> CARD_MODEL_ONE = Optional.empty();
    private static final Optional<String[]> CARD_MODEL_TWO = Optional.of(new String[]{"card", "4"});

    static CardModelParser cardModelParse;

    @BeforeAll
    static void setUp() {
        cardModelParse = new CardModelParser();
    }

    @Test
    void parseWhenStringNotContainsCard() {
        Optional<String[]> model = cardModelParse.parse(CARD_STRING_ONE);
        Assertions.assertEquals(CARD_MODEL_ONE, model, "Должен вернуть Optional.empty() когда не содержит card.");
    }

    @Test
    void parseWhenStringContainsCardAndHasValidData() {
        Optional<String[]> model = cardModelParse.parse(CARD_STRING_TWO);
        Assertions.assertArrayEquals(CARD_MODEL_TWO.get(), model.get(), "Должен вернуть массив строк когда содержит card и данные валидны.");
    }

    @Test
    void parseWhenStringContainsCardAndHasNotValidDataThrowProductException() {
        Assertions.assertThrows(CardException.class, () -> cardModelParse.parse(CARD_STRING_THREE), "Должно было упасть исключение, но его нет.");
    }
}