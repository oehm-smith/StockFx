package com.tintuna.stockfx.model;

import com.tintuna.stockfx.persistence.Portfolio;

public class ModelFactory {
	private PortfoliosModel portfolios;
	private StocksModel stocks;

	public PortfoliosModel getPortfoliosModel() {
		return PortfoliosModel.instance();
	}

	public StocksModel getStocksModel(Portfolio portfolio) {
		return PortfolioAssociatedStocks.getStockModelForAssociatedPortfolio(portfolio);
	}
	
	public StockFxPreferences getStockFxPreferences() {
		return StockFxPreferences.instance();
	}
}
