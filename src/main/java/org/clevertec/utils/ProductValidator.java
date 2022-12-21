package org.clevertec.utils;

import java.util.regex.Pattern;

import org.clevertec.constants.Constants;
import org.clevertec.exceptions.impl.ProductException;

/**
 * Класс валидатор модели продукта
 *
 * @author Viktar Kvasha
 */

public class ProductValidator {

    private ProductValidator() {
    }

    public static boolean isValid(final String productModel) {
        Pattern pattern = Pattern.compile(Constants.PRODUCT_MODEL_PATTERN);
        if (!pattern.matcher(productModel).matches()) {
            throw ProductException.builder().message("Модель данных продукта не валидна")
                    .model(productModel).build();
        }
        return true;
    }
}
