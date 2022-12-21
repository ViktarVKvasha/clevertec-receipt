package org.clevertec.services.impl;

import org.clevertec.dao.Dao;
import org.clevertec.dao.impl.CardDao;
import org.clevertec.model.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Optional;

class BaseCardScannerTest {
    private static final String CARD_STRING_ONE = "card-111";
    private static final String CARD_STRING_TWO = "card-112";
    private static final String CARD_STRING_THREE = "card-113";
    private static final Optional<String[]> CARD_MODEL_ONE = Optional.of(new String[]{"card", "111"});
    private static final Optional<String[]> CARD_MODEL_TWO = Optional.of(new String[]{"card", "112"});
    private static final Optional<String[]> CARD_MODEL_THREE = Optional.of(new String[]{"card", "113"});

    private static final Optional<Card> CARD_ONE = Optional.of(Card.builder().id(111).discount(3.00).build());

    private BaseCardScanner baseCardScanner;
    private static Dao<Card, Integer> cardDao;
    private static CardModelParser cardParser;

    @BeforeAll
    static void setUp() throws IOException {
        cardParser = Mockito.mock(CardModelParser.class);
        cardDao = Mockito.mock(CardDao.class);
        Mockito.when(cardParser.parse(CARD_STRING_ONE)).thenReturn(CARD_MODEL_ONE);
        Mockito.when(cardParser.parse(CARD_STRING_TWO)).thenReturn(CARD_MODEL_TWO);
        Mockito.when(cardParser.parse(CARD_STRING_THREE)).thenReturn(CARD_MODEL_THREE);
        Mockito.when(cardDao.get(111)).thenReturn(CARD_ONE);
        Mockito.when(cardDao.get(112)).thenReturn(Optional.empty());
        Mockito.when(cardDao.get(113)).thenThrow(IOException.class);
    }

    @BeforeEach
    public void beforeEach() {
        baseCardScanner = new BaseCardScanner(cardDao, cardParser);
    }

    @Test
    void scanWhenNoData() throws IOException {
        Optional<Card> card = baseCardScanner.scan(new String[]{});
        Assertions.assertTrue(card.isEmpty(), "Должен вернуть true, если на входе нету данных.");
    }

    @Test
    void scanWhenDataIsNotPresent() throws IOException {
        Optional<Card> card = baseCardScanner.scan(new String[]{"1-2"});
        Assertions.assertTrue(card.isEmpty(), "Должен вернуть true, если на входе есть данные, но не данные карты.");
    }

    @Test
    void scanWhenNoCardFound() throws IOException {
        Optional<Card> card = baseCardScanner.scan(new String[]{CARD_STRING_TWO});
        Assertions.assertTrue(card.isEmpty(), "Должен вернуть true, если на входе есть данные карты, но в источнике нету карты с заданными данными.");
    }

    @Test
    void scanWhenCardFound() throws IOException {
        Optional<Card> card = baseCardScanner.scan(new String[]{CARD_STRING_ONE});
        Assertions.assertEquals(CARD_ONE.get(), card.get(), "Должен вернуть Optional<Card> с эквивалентным объектом.");
    }

    @Test
    void scanWhenThrowException() {
        Assertions.assertThrows(IOException.class, () -> baseCardScanner.scan(new String[]{CARD_STRING_THREE}),
                "Должно было упасть исключение, но его нет.");
    }
}