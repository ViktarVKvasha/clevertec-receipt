package org.clevertec;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

import org.clevertec.constants.Constants;
import org.clevertec.controller.Controller;
import org.clevertec.exceptions.impl.CardException;
import org.clevertec.exceptions.impl.PathException;
import org.clevertec.exceptions.impl.PrintException;
import org.clevertec.exceptions.impl.ProductException;
import org.clevertec.factory.ControllerFactory;
import org.clevertec.factory.InputType;
import org.clevertec.factory.impl.ReceiptControllerFactory;
import org.clevertec.model.Receipt;
import org.clevertec.services.impl.ApplicationStartUpPath;

public class Runner {

    /**
     * Точка входа в программу
     *
     * @param args входные данные
     */

    public static void main(String[] args) {
        ApplicationStartUpPath startUpPath = new ApplicationStartUpPath();
        try {
            ControllerFactory<Receipt> factory = new ReceiptControllerFactory();
            factory.loadStartupPath(startUpPath.getApplicationStartUp().toString());
            Controller<Receipt> controller = factory.orderController(getArgumentType(args));
            Optional<Receipt> receipt = controller.generate(args);
            receipt.ifPresent(controller::print);
        } catch (IOException e) {
            System.out.println("Невозможно считать данные из источника. " + e.getMessage());
        } catch (ProductException | CardException | PathException e) {
            System.out.println(e.getMessage());
        } catch (PrintException e) {
            System.out.println("Невозможно распечатать данные чека. " + e.getMessage());
        }
    }

    private static InputType getArgumentType(String[] args) {
        Pattern pattern = Pattern.compile(Constants.PATH_TO_FILE_WINDOWS_PATTERN);
        return pattern.matcher(args[0]).matches() ? InputType.FILE : InputType.ARRAY;
    }
}