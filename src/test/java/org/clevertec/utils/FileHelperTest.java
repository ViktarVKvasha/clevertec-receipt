package org.clevertec.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileHelperTest {
    private static final Path existsPath = Path.of("D:\\mock.txt");
    private static final Path nonExistsPath = Path.of("D:\\folder\\mock.txt");
    private static final Path notExistsPathParent = nonExistsPath.getParent();

    @Test
    void createWhenFileExists() {
        try (MockedStatic<Files> filesMockedStatic = Mockito.mockStatic(Files.class)) {
            filesMockedStatic.when(() -> Files.exists(existsPath)).thenReturn(true);
            boolean result = FileHelper.create(existsPath);
            Assertions.assertTrue(result, "Должен вернуться true, если файл уже существует.");
        }
    }

    @Test
    void createWhenFileNotExists() {
        try (MockedStatic<Files> filesMockedStatic = Mockito.mockStatic(Files.class)) {
            filesMockedStatic.when(() -> Files.exists(nonExistsPath)).thenReturn(false).thenReturn(true);
            filesMockedStatic.when(() -> Files.createDirectories(notExistsPathParent)).thenReturn(notExistsPathParent);
            filesMockedStatic.when(() -> Files.createFile(nonExistsPath)).thenReturn(nonExistsPath);

            boolean result = FileHelper.create(nonExistsPath);

            filesMockedStatic.verify(() -> Files.createDirectories(notExistsPathParent), Mockito.times(1));
            filesMockedStatic.verify(() -> Files.createFile(nonExistsPath), Mockito.times(1));
            Assertions.assertTrue(result, "Должен вернуться true, если файл не существует и должны отработать методы по созданию директорий и файла по одному разу.");
        }
    }

    @Test
    void createWhenThrowException() {
        try (MockedStatic<Files> filesMockedStatic = Mockito.mockStatic(Files.class)) {
            filesMockedStatic.when(() -> Files.exists(nonExistsPath)).thenReturn(false).thenReturn(true);
            filesMockedStatic.when(() -> Files.createDirectories(notExistsPathParent)).thenThrow(IOException.class);
            filesMockedStatic.when(() -> Files.createFile(nonExistsPath)).thenReturn(nonExistsPath);

            boolean result = FileHelper.create(nonExistsPath);

            filesMockedStatic.verify(() -> Files.createDirectories(notExistsPathParent), Mockito.times(1));
            filesMockedStatic.verify(() -> Files.createFile(nonExistsPath), Mockito.times(0));
            Assertions.assertTrue(result, "Должно было упасть исключение, но его нет.");
        }
    }
}