package org.clevertec.services;

import java.io.IOException;

/**
 * @param <T> тип объекта
 * @author Viktar Kvasha
 */

public interface Scanner<T> {

    /**
     * Метод получения объекта
     *
     * @param args входные параметры(массив параметров / путь к файлу источнику данных)
     * @return словарь объектов
     */
    T scan(final String[] args) throws IOException;
}
