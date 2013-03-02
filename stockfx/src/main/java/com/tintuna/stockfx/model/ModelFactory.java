package com.tintuna.stockfx.model;

public class ModelFactory {
	private Portfolios portfolios;
	private Stocks stocks;
	
	public Portfolios getPortfolios() {
		if (portfolios == null) {
			portfolios = new Portfolios();
		}
		return portfolios;
	}
	
	public Stocks getStocks() {
		if (stocks == null) {
			stocks = new Stocks();
		}
		return stocks;
	}
}
