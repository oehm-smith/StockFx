package com.tintuna.stockfx.application;

import com.tintuna.stockfx.controller.MainController;
import com.tintuna.stockfx.controller.PortfolioController;
import com.tintuna.stockfx.controller.TabManager;
import com.tintuna.stockfx.db.Crud;

public class AppFactory {
	private MainController mainController;
	private TabManager tabManager;
	private PortfolioController portfolioController; 
	private Crud crudService;

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
	
	public PortfolioController getPortfolioController() {
		if (portfolioController == null) {
			portfolioController = new PortfolioController(this);
		}
		return portfolioController;
	}
	
	public Crud getCrudService() {
		if (crudService == null) {
			crudService = new Crud();
		}
		return crudService;
	}
}
