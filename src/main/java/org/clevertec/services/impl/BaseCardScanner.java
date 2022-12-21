package org.clevertec.services.impl;

import java.io.IOException;
import java.util.Optional;

import org.clevertec.dao.Dao;
import org.clevertec.model.Card;
import org.clevertec.services.Parser;
import org.clevertec.services.Scanner;

/**
 * Реализация интерфейса Scanner
 *
 * @author Viktar Kvasha
 * @see org.clevertec.services.Scanner
 */

public class BaseCardScanner implements Scanner<Optional<Card>> {

    private final Dao<Card, Integer> dao;

    private final Parser cardParser;

    public BaseCardScanner(Dao<Card, Integer> dao, Parser cardParser) {
        this.dao = dao;
        this.cardParser = cardParser;
    }

    @Override
    public Optional<Card> scan(String[] args) throws IOException {
        Optional<Card> card = Optional.empty();
        for (String arg : args) {
            Optional<String[]> maybeModel = cardParser.parse(arg);
            if (maybeModel.isPresent()) {
                String[] model = maybeModel.get();
                card = dao.get(Integer.parseInt(model[1]));
            }
        }
        return card;
    }
}
