package org.clevertec.constants;

/**
 * Константы
 *
 * @author Viktar Kvasha
 */

public final class Constants {

    private Constants() {
    }

    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String TIME_PATTERN = "hh:mm:ss";
    public static final String PATH_TO_FILE_WINDOWS_PATTERN = "([A-Za-z]:\\\\)((?:.*\\\\)?)([\\w\\s]+\\.\\w+)";
    public static final String PRODUCT_MODEL_PATTERN = "^(\\d+-{1})+\\d+$";
    public static final String CARD_MODEL_PATTERN = "^(card-{1})+\\d+$";
    public static final String SPLIT_CHAR = "-";
    public static final int PROMOTION_QUANTITY = 5;
}
