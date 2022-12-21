package org.clevertec.services;

import org.clevertec.exceptions.impl.PrintException;

/**
 * @param <T> объект данных
 * @author Viktar Kvasha
 */

public interface Printer<T> {

    /**
     * Метод вывода объекта
     *
     * @param obj объект
     */
    void print(final T obj) throws PrintException;
}
