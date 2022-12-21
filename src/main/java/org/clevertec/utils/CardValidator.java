package org.clevertec.utils;

import java.util.regex.Pattern;

import org.clevertec.constants.Constants;
import org.clevertec.exceptions.impl.CardException;

/**
 * Класс валидатор модели карты
 *
 * @author Viktar Kvasha
 */

public class CardValidator {

    private CardValidator() {
    }

    public static boolean isValid(final String cardModel) {
        Pattern pattern = Pattern.compile(Constants.CARD_MODEL_PATTERN);
        if (!pattern.matcher(cardModel).matches()) {
            throw CardException.builder().message("Модель данных скидочной карты не валидна")
                    .model(cardModel).build();
        }
        return true;
    }
}
