package org.clevertec.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.clevertec.exceptions.impl.PrintException;
import org.clevertec.model.Receipt;
import org.clevertec.services.Printer;
import org.clevertec.utils.FileHelper;
import org.clevertec.view.impl.BaseReceiptView;

/**
 * Реализация интерфейса Printer
 *
 * @author Viktar Kvasha
 * @see org.clevertec.services.Printer
 */

public class FilePrinter implements Printer<Receipt> {

    private final Path path;

    public FilePrinter(Path path) {
        this.path = path;
    }

    @Override
    public void print(Receipt receipt) throws PrintException {
        FileHelper.create(path);
        try {
            Files.write(path, new BaseReceiptView().generateView(receipt).getBytes());
        } catch (IOException e) {
            throw new PrintException("Ошибка записи данных в файл ", e);
        }
    }
}
