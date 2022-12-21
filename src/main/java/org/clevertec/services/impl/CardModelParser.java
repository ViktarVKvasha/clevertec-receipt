package org.clevertec.services.impl;

import java.util.Optional;

import org.clevertec.constants.Constants;
import org.clevertec.services.Parser;
import org.clevertec.utils.CardValidator;

/**
 * Реализация интерфейса Parser
 *
 * @author Viktar Kvasha
 * @see org.clevertec.services.Parser
 */

public class CardModelParser implements Parser {

    @Override
    public Optional<String[]> parse(String data) {
        if (data.toLowerCase().contains("card") && CardValidator.isValid(data.toLowerCase())) {
            return Optional.of(data.split(Constants.SPLIT_CHAR));
        }
        return Optional.empty();
    }
}
