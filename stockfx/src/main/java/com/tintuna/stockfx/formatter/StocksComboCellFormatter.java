package com.tintuna.stockfx.formatter;

import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;

import com.tintuna.stockfx.persistence.PortfolioStock;

public class StocksComboCellFormatter extends ListCell<PortfolioStock> {
	public StocksComboCellFormatter() {

	}

	protected void updateItem(PortfolioStock portfolioStock, boolean empty) {
		// calling super here is very important - don't skip this!
		super.updateItem(portfolioStock, empty);

		if (portfolioStock == null) {
		} else {
			setText(portfolioStock.getStock().getSymbol() + " - " + portfolioStock.getStock().getCompanyName());
			setTextFill(Color.BLACK);
		}
	}
}