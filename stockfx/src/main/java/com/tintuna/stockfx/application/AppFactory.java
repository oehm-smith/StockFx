package com.tintuna.stockfx.application;

import java.io.FileNotFoundException;

import com.tintuna.stockfx.controller.CollectionsController;
import com.tintuna.stockfx.controller.MainController;
import com.tintuna.stockfx.controller.PortfolioController;
import com.tintuna.stockfx.controller.PortfoliosController;
import com.tintuna.stockfx.controller.PreferencesController;
import com.tintuna.stockfx.controller.StockController;
import com.tintuna.stockfx.db.Crud;
import com.tintuna.stockfx.ui.tab.TabManager;

public class AppFactory {
	private MainController mainController;
	private TabManager tabManager;
	private PortfoliosController portfoliosController;
	private StockController stockController;
	private Crud crudService;
	private PortfolioController portfolioController;
	private PreferencesController prefsController;
	private CollectionsController collectionsController;

	public MainController getMainController() {
		if (mainController == null) {
			mainController = new MainController();
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
			portfoliosController = new PortfoliosController();
		}
		return portfoliosController;
	}

	public PortfolioController getPortfolioController() {
		if (portfolioController == null) {
			portfolioController = new PortfolioController();
		}
		return portfolioController;
	}

	public CollectionsController getCollectionsController() {
		if (collectionsController == null) {
			collectionsController = new CollectionsController();
		}
		return collectionsController;
	}

	public StockController getStockController() {
		if (stockController == null) {
			stockController = new StockController(); // even if one already exists - start new one each time
		}
		return stockController;
	}

	public PreferencesController getPrefsController() {
		if (prefsController == null) {
			prefsController = new PreferencesController();
		}
		return prefsController;
	}

	public Crud getCrudService() {
		if (crudService == null) {
			try {
				crudService = new Crud();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return crudService;
	}
}
