package com.tintuna.stockfx.model;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.persistence.Stock;

public class Portfolios {
	private ObservableList<Portfolio> portfolios;

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

	public ObservableList<Stock> getPortfoliosStocks(Portfolio p) {
		System.out.println("-> getPortfoliosStocks - they are:"+p.getobservableStocksInThisPortfolio());
		return p.getobservableStocksInThisPortfolio();
	}
	
	public void newPortfolio() {
		Portfolio p = new Portfolio("New Portfolio", "Work");
		MainApplication.getServiceFactory().getPortfolioService().create(p);
		updatePortfolioList(p);
	}
}
