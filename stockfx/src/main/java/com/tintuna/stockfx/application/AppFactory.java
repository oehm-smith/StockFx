package com.tintuna.stockfx.application;

import com.tintuna.stockfx.controller.MainController;
import com.tintuna.stockfx.controller.PortfolioController;
import com.tintuna.stockfx.controller.PortfoliosController;
import com.tintuna.stockfx.controller.StockController;
import com.tintuna.stockfx.controller.TabManager;
import com.tintuna.stockfx.db.Crud;

public class AppFactory {
	private MainController mainController;
	private TabManager tabManager;
	private PortfoliosController portfoliosController;
	private StockController stockController;
	private Crud crudService;
	private PortfolioController portfolioController;

	public MainController getMainController() {
		if (mainController == null) {
			mainController = new MainController(this);
		}
		return mainController;
	}

	public TabManager getTabManager() {
		if (tabManager == null) {
			tabManager = new TabManager();
		}
		return tabManager;
	}

	public PortfoliosController getPortfoliosController() {
		if (portfoliosController == null) {
			portfoliosController = new PortfoliosController(this);
		}
		return portfoliosController;
	}

	public PortfolioController getPortfolioController() {
		if (portfolioController == null) {
			portfolioController = new PortfolioController(this);
		}
		return portfolioController;
	}

	public StockController getStockController() {
		stockController = new StockController(this); // even if one already exists - start new one each time
		return stockController;
	}

	public Crud getCrudService() {
		if (crudService == null) {
			crudService = new Crud();
		}
		return crudService;
	}
}
