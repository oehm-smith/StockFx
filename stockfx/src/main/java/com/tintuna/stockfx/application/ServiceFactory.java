package com.tintuna.stockfx.application;

import com.tintuna.stockfx.service.PortfolioService;

public class ServiceFactory {
	private PortfolioService portfolioService;

	public PortfolioService getPortfolioService() {
		if (portfolioService == null) {
			portfolioService = new PortfolioService();
		}
		return portfolioService;
	}
}
