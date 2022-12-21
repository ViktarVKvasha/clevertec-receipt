package org.clevertec.dao;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @param <T> класс объекта
 * @param <K> ключ поиска
 * @author Viktar Kvasha
 */

public interface Dao<T, K> {

    /**
     * Метод получения объекта по ключу
     *
     * @param key - ключ поиска
     * @return объект
     */
    Optional<T> get(final K key) throws IOException;

    /**
     * Метод получения списка объектов
     *
     * @return список объектов
     */
    List<T> getAll() throws IOException;

    /**
     * Метод сохранения объекта в источник данных
     *
     * @param obj - объект
     * @return подтверждение
     */
    boolean save(final T obj) throws IOException;

    /**
     * Метод обновления объекта в источнике данных
     *
     * @param obj - объект
     * @return подтверждение
     */
    boolean update(final T obj) throws IOException;

    /**
     * Метод удаления объекта в источнике данных
     *
     * @param obj - объект
     * @return подтверждение
     */
    boolean delete(final T obj) throws IOException;
}
