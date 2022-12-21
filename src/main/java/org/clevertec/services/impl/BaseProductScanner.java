package org.clevertec.services.impl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.clevertec.dao.Dao;
import org.clevertec.model.Product;
import org.clevertec.services.Parser;
import org.clevertec.services.Scanner;

/**
 * Реализация интерфейса Scanner
 *
 * @author Viktar Kvasha
 * @see org.clevertec.services.Scanner
 */

public class BaseProductScanner implements Scanner<Map<Product, Integer>> {

    private final Dao<Product, Integer> productDao;
    private final Parser productParser;

    public BaseProductScanner(Dao<Product, Integer> dao, Parser productParser) {
        this.productDao = dao;
        this.productParser = productParser;
    }

    @Override
    public Map<Product, Integer> scan(String[] args) throws IOException {
        Map<Product, Integer> products = new LinkedHashMap<>();
        for (String arg : args) {
            Optional<String[]> maybeMode = productParser.parse(arg);
            if (maybeMode.isPresent()) {
                String[] model = maybeMode.get();
                Optional<Product> product = productDao.get(Integer.parseInt(model[0]));
                product.ifPresent(value -> products.put(value, Integer.parseInt(model[1])));
            }
        }
        return products;
    }
}
