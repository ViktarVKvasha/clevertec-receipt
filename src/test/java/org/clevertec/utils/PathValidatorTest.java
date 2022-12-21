package org.clevertec.utils;

import org.clevertec.exceptions.impl.PathException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PathValidatorTest {
  private static final String PATH_STRING_ONE = "C:\\Windows\\sys32.txt";
  private static final String PATH_STRING_TWO = "C:/Windows\\sys32._  txt";

  @Test
  void isValid() {
    Assertions.assertTrue(PathValidator.isValid(PATH_STRING_ONE),
        "Должен вернуться true, если данные валидны.");
  }

  @Test
  void whenDataNotValidThrowException() {
    Assertions.assertThrows(PathException.class,
        () -> PathValidator.isValid(PATH_STRING_TWO),
        "Должно было упасть исключение, но его нет.");
  }
}