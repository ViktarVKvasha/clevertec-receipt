package org.clevertec.services.impl;

import java.util.Optional;

import org.clevertec.constants.Constants;
import org.clevertec.services.Parser;
import org.clevertec.utils.ProductValidator;

/**
 * Реализация интерфейса Parser
 *
 * @author Viktar Kvasha
 * @see org.clevertec.services.Parser
 */

public class ProductModelParser implements Parser {

    @Override
    public Optional<String[]> parse(String data) {
        if (!data.toLowerCase().contains("card") && ProductValidator.isValid(data)) {
            return Optional.of(data.split(Constants.SPLIT_CHAR));
        }
        return Optional.empty();
    }
}
