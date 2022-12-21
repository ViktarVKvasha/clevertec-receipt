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
import org.clevertec.model.Card;
import org.clevertec.utils.FileHelper;

/**
 * Реализация интерфейса Dao<T, K>
 *
 * @author Viktar Kvasha
 * @see org.clevertec.dao.Dao
 */

public class CardDao implements Dao<Card, Integer> {

    private final Path path;
    private final Gson gson;

    public CardDao(Path path) {
        this.path = path;
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public Optional<Card> get(Integer key) throws IOException {
        return getAll().stream().filter(c -> c.getId() == key).findAny();
    }

    @Override
    public List<Card> getAll() throws IOException {
        List<Card> cards = new ArrayList<>();
        Type type = new TypeToken<ArrayList<Card>>() {
        }.getType();
        if (FileHelper.exists(path)) {
            try (Reader reader = new FileReader(path.toString().trim(), StandardCharsets.UTF_8)) {
                cards = gson.fromJson(reader, type);
            }
        }
        FileHelper.create(path);
        return cards;
    }

    @Override
    public boolean save(Card obj) throws IOException {
        if (FileHelper.exists(path)) {
            List<Card> cards = getAll();

            cards.add(obj);

            try (Writer writer = new FileWriter(path.toString().trim())) {
                gson.toJson(cards, writer);
            }
            return true;
        }
        FileHelper.create(path);
        return false;
    }

    @Override
    public boolean update(Card obj) throws IOException {
        if (FileHelper.exists(path)) {
            Path temp = Files.createTempFile(path.getParent(), null, ".json");
            List<Card> cards = getAll();
            cards.stream().filter(c -> c.getId() == obj.getId()).findAny()
                    .ifPresent(c -> cards.set(cards.indexOf(c), obj));
            try (Writer writer = new FileWriter(temp.toString().trim())) {
                gson.toJson(cards, writer);
            }
            return FileHelper.delete(path) && FileHelper.rename(temp, path.toFile().getName());
        }
        FileHelper.create(path);
        return false;
    }

    @Override
    public boolean delete(Card obj) throws IOException {
        if (FileHelper.exists(path)) {
            Path temp = Files.createTempFile(path.getParent(), null, ".json");
            List<Card> cards = getAll();
            cards.remove(obj);
            try (Writer writer = new FileWriter(temp.toString().trim())) {
                gson.toJson(cards, writer);
            }
            return FileHelper.delete(path) && FileHelper.rename(temp, path.toFile().getName());
        }
        FileHelper.create(path);
        return false;
    }
}
