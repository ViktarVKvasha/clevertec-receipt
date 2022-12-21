package org.clevertec.services;

import java.util.Optional;

/**
 * Интерфейс парсинга строки с входными данными
 *
 * @author Viktar Kvasha
 */

public interface Parser {

    /**
     * Метод парсинга входных данных
     *
     * @param data строка входных данных
     * @return массив строк с преобразованными данными
     */
    Optional<String[]> parse(String data);
}
