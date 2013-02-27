package com.tintuna.stockfx.application;

import com.tintuna.stockfx.service.PortfolioService;
import com.tintuna.stockfx.service.StockService;

public class ServiceFactory {
	private PortfolioService portfolioService;
	private StockService stockService;

	public PortfolioService getPortfolioService() {
		if (portfolioService == null) {
			portfolioService = new PortfolioService();
		}
		return portfolioService;
	}
	
	public StockService getStockService() {
		if (stockService == null) {
			stockService = new StockService();
		}
		return stockService;
	}
}
