package org.clevertec.services.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.clevertec.dao.Dao;
import org.clevertec.model.Card;
import org.clevertec.services.Parser;
import org.clevertec.utils.FileHelper;
import org.clevertec.utils.PathValidator;

/**
 * Расширение класса BaseCardScanner
 *
 * @author Viktar Kvasha
 * @see BaseCardScanner
 */

public class FileCardScanner extends BaseCardScanner {

    public FileCardScanner(Dao<Card, Integer> dao, Parser cardParser) {
        super(dao, cardParser);
    }

    @Override
    public Optional<Card> scan(String[] args) throws IOException {
        List<String> models = new ArrayList<>();
        if (PathValidator.isValid(args[0]) && FileHelper.exists(
                Path.of(args[0]))) {
            try (BufferedReader reader = new BufferedReader(
                    new FileReader(args[0].trim(), StandardCharsets.UTF_8))) {
                String model;
                while ((model = reader.readLine()) != null) {
                    models.add(model);
                }
            } catch (IOException e) {
                System.out.println("Ошибка чтения данных с файла " + e.getMessage());
            }
        }
        return super.scan(models.toArray(new String[0]));
    }
}
