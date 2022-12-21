package org.clevertec.view.impl;

import com.wagu.Block;
import com.wagu.Board;
import com.wagu.Table;
import org.clevertec.constants.Constants;
import org.clevertec.model.Product;
import org.clevertec.model.Receipt;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Базовое представление чека
 *
 * @author Viktar Kvasha
 */

public class BaseReceiptView {

    public BaseReceiptView() {
    }

    public String generateView(Receipt receipt) {
        String company = receipt.getCompany() + "\n";
        List<String> t1Header = Arrays.asList("Receipt's ID", "Date/Time");
        List<List<String>> t1Rows = List.of(
                List.of(String.valueOf(receipt.getId()),
                        receipt.getDate().format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN))),
                List.of(" ", receipt.getTime().format(DateTimeFormatter.ofPattern(Constants.TIME_PATTERN)))
        );

        String details = "SELLING DETAILS";
        List<String> t2Header = List.of("DESCRIPTION", "QTE",
                "PRICE($)", "DISCOUNT(%)",
                "TOTAL PRICE($)", "TOTAL DISCOUNT($)");
        List<List<String>> t2Rows = new ArrayList<>();
        for (Product product : receipt.getProducts().keySet()) {
            t2Rows.add(
                    List.of(product.getDescription(), String.valueOf(receipt.getProducts().get(product)),
                            product.getPrice().toString(), String.valueOf(product.getDiscount()),
                            receipt.getProxyCashReports().get(product.getId()).getPrice().toString(),
                            receipt.getProxyCashReports().get(product.getId()).getDiscount().toString()
                    ));
        }

        String summary = """
                DISCOUNT CARD(%):\s
                TOTAL PRICE($):\s
                TOTAL DISCOUNT($):\s
                FINAL PRICE($):\s""";

        String value = (receipt.getDiscountCard() != null ? String.valueOf(receipt.getDiscountCard().getDiscount()) : 0.00)
                + "\n" + receipt.getTotalPrice() + "\n" + receipt.getTotalDiscount() + "\n"
                + receipt.getFinalPrice();

        Board board = new Board(112);
        board.setInitialBlock(new Block(board, 110, 2, company)
                .allowGrid(false)
                .setBlockAlign(Block.BLOCK_CENTRE)
                .setDataAlign(Block.DATA_CENTER));
        board.appendTableTo(0, Board.APPEND_BELOW, new Table(board, 112, t1Header, t1Rows));
        board.getBlock(3)
                .setBelowBlock(new Block(board, 110, 1, details).setDataAlign(Block.DATA_CENTER));
        board.appendTableTo(5, Board.APPEND_BELOW, new Table(board, 112, t2Header, t2Rows));

        Block summaryBlock = new Block(board, 98, 5, summary).allowGrid(false)
                .setDataAlign(Block.DATA_MIDDLE_RIGHT);
        board.getBlock(12).setBelowBlock(summaryBlock);
        Block valueBlock = new Block(board, 12, 5, value).allowGrid(false)
                .setDataAlign(Block.DATA_MIDDLE_RIGHT);
        summaryBlock.setRightBlock(valueBlock);

        return board.invalidate().build().getPreview();
    }
}
