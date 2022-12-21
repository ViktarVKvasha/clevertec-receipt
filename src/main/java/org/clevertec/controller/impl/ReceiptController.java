package org.clevertec.controller.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.clevertec.controller.Controller;
import org.clevertec.exceptions.impl.PrintException;
import org.clevertec.model.Card;
import org.clevertec.model.Product;
import org.clevertec.model.ProxyCashReport;
import org.clevertec.model.Receipt;
import org.clevertec.services.ArithmeticKernel;
import org.clevertec.services.Printer;
import org.clevertec.services.Scanner;

/**
 * Класс контроллер чеков
 *
 * @author Viktar Kvasha
 * @see org.clevertec.controller.Controller
 */

public record ReceiptController(ArithmeticKernel arithmeticKernel,
                                Scanner<Map<Product, Integer>> productScanner,
                                Scanner<Optional<Card>> cardScanner,
                                Printer<Receipt> consolePrinter,
                                Printer<Receipt> filePrinter) implements
        Controller<Receipt> {

    public Optional<Receipt> generate(String[] args) throws IOException {
        Map<Product, Integer> products = productScanner.scan(args);
        Optional<Card> card = cardScanner.scan(args);
        Map<Integer, ProxyCashReport> cashReports = arithmeticKernel.calculateProxyCashReports(products,
                card.orElse(null));
        BigDecimal totalPrice = arithmeticKernel.calculateTotalPrice(products);
        BigDecimal totalDiscount = arithmeticKernel.calculateTotalDiscount(products, card.orElse(null));

        Receipt receipt = Receipt.builder().id(new Random().nextInt(Integer.MAX_VALUE)).company("Clevertec")
                .date(LocalDate.now()).time(LocalTime.now())
                .products(products).discountCard(card.orElse(null))
                .proxyCashReports(cashReports).totalPrice(totalPrice).totalDiscount(totalDiscount)
                .finalPrice(totalPrice.subtract(totalDiscount))
                .build();
        return Optional.of(receipt);
    }

    public void print(final Receipt receipt) throws PrintException {
        consolePrinter.print(receipt);
        filePrinter.print(receipt);
    }
}
