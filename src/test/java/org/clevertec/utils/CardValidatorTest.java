package org.clevertec.utils;

import org.clevertec.exceptions.impl.CardException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardValidatorTest {
  private static final String CARD_STRING_ONE = "card-124343";
  private static final String CARD_STRING_TWO = "mock-1232132";

  @Test
  void isValid() {
    Assertions.assertTrue(CardValidator.isValid(CARD_STRING_ONE),
        "Должен вернуть true, если данные валидны.");
  }

  @Test
  void whenDataNotValidThrowException() {
    Assertions.assertThrows(CardException.class, () -> CardValidator.isValid(CARD_STRING_TWO),
        "Должно было упасть исключение, но его нет.");
  }
}