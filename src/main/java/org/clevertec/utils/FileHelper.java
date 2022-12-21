package org.clevertec.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Утильный класс работы с файлами
 *
 * @author Viktar Kvasha
 */

public class FileHelper {

    private FileHelper() {
    }

    /**
     * Метод создания файла
     *
     * @param path путь к файлу
     * @return подтверждение создания файла
     */
    public static boolean create(final Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                System.out.println("Ошибка создания файла. " + e.getMessage());
            }
        }
        return Files.exists(path);
    }

    /**
     * Метод перименования файла
     *
     * @param path путь к файлу
     * @param name новое имя файла
     * @return подтверждение переименования файла
     */
    public static boolean rename(final Path path, final String name) {
        Path rPath = path.resolveSibling("dummy");
        try {
            rPath = Files.move(path, path.resolveSibling(name));
        } catch (IOException e) {
            System.out.println("Ошибка переименования файла. " + e.getMessage());
        }
        return Files.exists(rPath);
    }

    /**
     * Метод удаления файла
     *
     * @param path путь к файлу
     * @return подтверждение удаления файла
     */
    public static boolean delete(final Path path) {
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                System.out.println("Ошибка удаления файла. " + e.getMessage());
            }
        }
        return !Files.exists(path);
    }

    /**
     * Метод проверки существования файла(обертка)
     *
     * @param path путь к файлу
     * @return подтверждение существования файла
     */
    public static boolean exists(final Path path) {
        return Files.exists(path);
    }
}
