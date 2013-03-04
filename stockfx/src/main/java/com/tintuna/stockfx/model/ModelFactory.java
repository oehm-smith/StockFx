package com.tintuna.stockfx.model;

import com.tintuna.stockfx.persistence.Portfolio;

public class ModelFactory {
	private Portfolios portfolios;
	private Stocks stocks;

	public Portfolios getPortfolios() {
		if (portfolios == null) {
			portfolios = new Portfolios();
		}
		return portfolios;
	}

	public Stocks getStocks(Portfolio portfolio) {
		return PortfolioAssociatedStocks.getStockModelForAssociatedPortfolio(portfolio);
	}
}
