package org.clevertec.controller;

import java.io.IOException;
import java.util.Optional;

import org.clevertec.exceptions.impl.PrintException;

/**
 * @param <T> класс объекта
 * @author Viktar Kvasha
 */

public interface Controller<T> {

    Optional<T> generate(String[] args) throws IOException;

    void print(final T receipt) throws PrintException;
}
