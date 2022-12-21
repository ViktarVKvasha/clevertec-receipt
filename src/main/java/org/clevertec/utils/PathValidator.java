package org.clevertec.utils;

import java.util.regex.Pattern;

import org.clevertec.constants.Constants;
import org.clevertec.exceptions.impl.PathException;

/**
 * Класс валидатор пути к файлу
 *
 * @author Viktar Kvasha
 */

public class PathValidator {

    private PathValidator() {
    }

    public static boolean isValid(final String pathToFile) {
        Pattern pattern = Pattern.compile(Constants.PATH_TO_FILE_WINDOWS_PATTERN);
        if (!pattern.matcher(pathToFile).matches()) {
            throw PathException.builder().message("Путь к файлу не валиден").path(pathToFile).build();
        }
        return true;
    }
}
