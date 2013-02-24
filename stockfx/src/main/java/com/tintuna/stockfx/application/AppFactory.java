package com.tintuna.stockfx.application;

import com.tintuna.stockfx.controller.MainController;
import com.tintuna.stockfx.controller.PortfolioController;
import com.tintuna.stockfx.controller.TabManager;
import com.tintuna.stockfx.db.Crud;
import com.tintuna.stockfx.model.PortfolioLoader;

public class AppFactory {
	private MainController mainController;
	private TabManager tabManager;
	private PortfolioController portfolioController; 
	private PortfolioLoader portfolioLoader;
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
	
	// TODO - use Injection which will allow for @Alternatives
	public void setPortfolioLoader(final PortfolioLoader portfolioLoader) {
		this.portfolioLoader = portfolioLoader;
	}
	
	public PortfolioLoader getPortfolioLoader() {
		if (portfolioLoader == null) {
			throw new RuntimeException("portfolioLoader needs to be set");
		}
		return portfolioLoader;
	}

	public Crud getCrudService() {
		if (crudService == null) {
			crudService = new Crud();
		}
		return crudService;
	}
}
