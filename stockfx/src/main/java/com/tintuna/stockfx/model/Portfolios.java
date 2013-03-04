package com.tintuna.stockfx.model;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.persistence.Stock;

public class Portfolios {
	private ObservableList<Portfolio> portfolios;
	private Portfolio selected = null;
	private StringProperty selectPortfolioNameProperty;

	public Portfolios() {
		portfolios = FXCollections.observableArrayList();
		updatePortfoliosAll();
	}

	public void updatePortfolioList(Portfolio p) {
		portfolios.add(p);
	}

	public void updatePortfoliosAll() {
		List<Portfolio> portList = MainApplication.getServiceFactory().getPortfolioService().findAll();
		portfolios.clear();
		portfolios.addAll(portList);
	}

	public ObservableList<Portfolio> getPortfolios() {
		return portfolios;
	}

	public void setPortfolios(ObservableList<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

	public void addPortfoliosListener(ListChangeListener listener) {
		portfolios.addListener(listener);
	}

	public Portfolio getSelected() {
		return selected;
	}

	public void setSelected(Portfolio selected) {
		this.selected = selected;
		setSelectPortfolioNamePropertyText(selected.getName());
	}

	public StringProperty getSelectPortfolioNameProperty() {
		if (selectPortfolioNameProperty == null) {
			selectPortfolioNameProperty = new SimpleStringProperty();
		}
		return selectPortfolioNameProperty;
	}
	
	public void setSelectPortfolioNameProperty(StringProperty selectPortfolioNameProperty) {
		this.selectPortfolioNameProperty = selectPortfolioNameProperty;
	}

	public void setSelectPortfolioNamePropertyText(String text) {
		getSelectPortfolioNameProperty().set(text);
	}
	
	public ObservableList<Stock> getPortfoliosStocks(Portfolio p) {
		System.out.println("-> getPortfoliosStocks - they are:" + p.getobservableStocksInThisPortfolio());
		return p.getobservableStocksInThisPortfolio();
	}

	public ObservableList<Stock> getPortfoliosStocksForSelected() {
		if (getSelected() == null) {
			return null;
		}
		System.out.println("-> getPortfoliosStocksForSelected - they are:"
				+ getSelected().getobservableStocksInThisPortfolio());
		return getSelected().getobservableStocksInThisPortfolio();
	}

	public void newPortfolio(String newPortfolioName, String type) {
		Portfolio portfolio = new Portfolio(newPortfolioName, type);
		MainApplication.getServiceFactory().getPortfolioService().create(portfolio);
		updatePortfolioList(portfolio);
	}

	public void addStockToSelectedPortfolio(Stock stock) {
		if (getSelected() != null) {
			// EntityManager em = MainApplication.openTransaction();
			// em.getTransaction().begin();
			getSelected().addStock(stock);
			MainApplication.getAppFactory().getCrudService().update(getSelected());
			MainApplication.databaseDebugPrintout();

			// MainApplication.endTransaction(em);
		}
	}
}
