package org.clevertec.services.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.clevertec.dao.Dao;
import org.clevertec.model.Product;
import org.clevertec.services.Parser;
import org.clevertec.utils.FileHelper;
import org.clevertec.utils.PathValidator;

/**
 * Расширение класса BaseProductScanner
 *
 * @author Viktar Kvasha
 * @see org.clevertec.services.impl.BaseProductScanner
 */

public class FileProductScanner extends BaseProductScanner {

    public FileProductScanner(Dao<Product, Integer> dao, Parser productParser) {
        super(dao, productParser);
    }

    @Override
    public Map<Product, Integer> scan(String[] args) throws IOException {
        List<String> models = new ArrayList<>();
        if (PathValidator.isValid(args[0]) && FileHelper.exists(
                Path.of(args[0]))) {
            try (BufferedReader reader = new BufferedReader(
                    new FileReader(args[0].trim(), StandardCharsets.UTF_8))) {
                String model;
                while ((model = reader.readLine()) != null) {
                    models.add(model);
                }
            }
        }
        return super.scan(models.toArray(new String[0]));
    }
}
