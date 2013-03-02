package com.tintuna.stockfx.formatter;

import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;

import com.tintuna.stockfx.persistence.Stock;

public class StocksComboCellFormatter extends ListCell<Stock> {
	public StocksComboCellFormatter() {

	}

	protected void updateItem(Stock stock, boolean empty) {
		// calling super here is very important - don't skip this!
		super.updateItem(stock, empty);

		if (stock == null) {
			setText("null buddy");
		} else {
			setText(stock.getSymbol() + " - " + stock.getCompanyName());
			setTextFill(Color.BLACK);
		}
	}
}