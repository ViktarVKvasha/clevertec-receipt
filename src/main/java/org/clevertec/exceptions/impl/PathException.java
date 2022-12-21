package org.clevertec.exceptions.impl;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Класс исключения для пути к файлу данных
 *
 * @author Viktar Kvasha
 */

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
public class PathException extends RuntimeException {

    private String path;

    @Builder
    public PathException(String message, String path) {
        super(message);
        this.path = path;
    }
}
