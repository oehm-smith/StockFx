package com.tintuna.stockfx.model;

import javafx.collections.ObservableList;

public class ModelFactory {
	private Portfolios portfolios;
	
	public Portfolios getPortfolios() {
		if (portfolios == null) {
			portfolios = new Portfolios();
		}
		return portfolios;
	}
}
