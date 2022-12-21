package org.clevertec.dao.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.clevertec.dao.Dao;
import org.clevertec.model.Product;
import org.clevertec.utils.FileHelper;

/**
 * Реализация интерфейса Dao<T, K>
 *
 * @author Viktar Kvasha
 * @see org.clevertec.dao.Dao
 */

public class ProductDao implements Dao<Product, Integer> {

    private final Path path;
    private final Gson gson;

    public ProductDao(Path path) {
        this.path = path;
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public Optional<Product> get(Integer key) throws IOException {
        return getAll().stream().filter(p -> p.getId() == key).findAny();
    }

    @Override
    public List<Product> getAll() throws IOException {
        List<Product> products = new ArrayList<>();
        Type type = new TypeToken<ArrayList<Product>>() {
        }.getType();
        if (FileHelper.exists(path)) {
            try (Reader reader = new FileReader(path.toString().trim(), StandardCharsets.UTF_8)) {
                products = gson.fromJson(reader, type);
            }
        }
        FileHelper.create(path);
        return products;
    }

    @Override
    public boolean save(Product obj) throws IOException {
        if (FileHelper.exists(path)) {
            List<Product> products = getAll();
            products.add(obj);
            try (Writer writer = new FileWriter(path.toString().trim())) {
                gson.toJson(products, writer);
            }
            return true;
        }
        FileHelper.create(path);
        return false;
    }

    @Override
    public boolean update(Product obj) throws IOException {
        if (FileHelper.exists(path)) {
            Path temp = Files.createTempFile(path.getParent(), null, ".json");
            List<Product> products = getAll();
            products.stream().filter(p -> p.getId() == obj.getId()).findAny()
                    .ifPresent(p -> products.set(products.indexOf(p), obj));
            try (Writer writer = new FileWriter(temp.toString().trim())) {
                gson.toJson(products, writer);
            }
            return FileHelper.delete(path) && FileHelper.rename(temp, path.toFile().getName());
        }
        FileHelper.create(path);
        return false;
    }

    @Override
    public boolean delete(Product obj) throws IOException {
        if (FileHelper.exists(path)) {
            Path temp = Files.createTempFile(path.getParent(), null, ".json");
            List<Product> products = getAll();
            products.remove(obj);
            try (Writer writer = new FileWriter(temp.toString().trim())) {
                gson.toJson(products, writer);
            }
            return FileHelper.delete(path) && FileHelper.rename(temp, path.toFile().getName());
        }
        FileHelper.create(path);
        return false;
    }
}
