package org.clevertec.exceptions.impl;

/**
 * Класс исключения для принтера
 *
 * @author Viktar Kvasha
 */

public class PrintException extends RuntimeException {

    public PrintException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrintException(Throwable cause) {
        super(cause);
    }

    public PrintException() {
        super();
    }

    public PrintException(String message) {
        super(message);
    }
}