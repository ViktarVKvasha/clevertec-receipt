package org.clevertec.services.impl;

import org.clevertec.model.Receipt;
import org.clevertec.services.Printer;
import org.clevertec.view.impl.BaseReceiptView;

/**
 * Реализация интерфейса Printer
 *
 * @author Viktar Kvasha
 * @see org.clevertec.services.Printer
 */

public class ConsolePrinter implements Printer<Receipt> {

    @Override
    public void print(Receipt receipt) {
        System.out.println(new BaseReceiptView().generateView(receipt));
    }
}
