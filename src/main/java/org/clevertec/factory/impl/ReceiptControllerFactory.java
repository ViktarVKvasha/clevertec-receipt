package org.clevertec.factory.impl;

import org.clevertec.controller.Controller;
import org.clevertec.controller.impl.ReceiptController;
import org.clevertec.dao.impl.CardDao;
import org.clevertec.dao.impl.ProductDao;
import org.clevertec.factory.ControllerFactory;
import org.clevertec.factory.InputType;
import org.clevertec.model.Receipt;
import org.clevertec.services.impl.*;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Реализация абстрактной фабрики контроллеров
 *
 * @author Viktar Kvasha
 * @see org.clevertec.factory.ControllerFactory
 */

public class ReceiptControllerFactory extends ControllerFactory<Receipt> {

    @Override
    protected Controller<Receipt> createController(InputType inputType) {
        return switch (inputType) {
            case ARRAY -> new ReceiptController(new BaseArithmeticKernel(),
                    new BaseProductScanner(new ProductDao(Path.of(new File(
                            Objects.requireNonNull(getClass().getClassLoader().getResource("products.json"))
                                    .getFile()).getPath())), new ProductModelParser()),
                    new BaseCardScanner(new CardDao(Path.of(new File(
                            Objects.requireNonNull(getClass().getClassLoader().getResource("cards.json"))
                                    .getFile()).getPath())), new CardModelParser()),
                    new ConsolePrinter(),
                    new FilePrinter(
                            Path.of(startupPath + FileSystems.getDefault().getSeparator() + "receipt.txt"))
            );
            case FILE -> new ReceiptController(new BaseArithmeticKernel(),
                    new FileProductScanner(new ProductDao(Path.of(new File(
                            Objects.requireNonNull(getClass().getClassLoader().getResource("products.json"))
                                    .getFile()).getPath())), new ProductModelParser()),
                    new FileCardScanner(new CardDao(Path.of(new File(
                            Objects.requireNonNull(getClass().getClassLoader().getResource("cards.json"))
                                    .getFile()).getPath())), new CardModelParser()),
                    new ConsolePrinter(),
                    new FilePrinter(
                            Path.of(startupPath + FileSystems.getDefault().getSeparator() + "receipt.txt"))
            );
        };
    }
}
