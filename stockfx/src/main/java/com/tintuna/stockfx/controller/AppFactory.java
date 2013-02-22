package com.tintuna.stockfx.controller;

public class AppFactory {
	private MainController mainController;
	private TabManager tabManager;
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
	
	public PortfolioController getPortfolioController() {
		if (portfolioController == null) {
			portfolioController = new PortfolioController(this);
		}
		return portfolioController;
	}
}
