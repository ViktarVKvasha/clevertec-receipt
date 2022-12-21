package org.clevertec.utils;

import org.clevertec.exceptions.impl.ProductException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductValidatorTest {
  private static final String PRODUCT_STRING_ONE = "1-5";
  private static final String PRODUCT_STRING_TWO = " - 5";

  @Test
  void isValid() {
    Assertions.assertTrue(ProductValidator.isValid(PRODUCT_STRING_ONE),
        "Должен вернуть true, если данные валидны.");
  }

  @Test
  void whenDataNotValidThrowException() {
    Assertions.assertThrows(ProductException.class, () -> ProductValidator.isValid(PRODUCT_STRING_TWO),
        "Должно было упасть исключение, но его нет.");
  }
}