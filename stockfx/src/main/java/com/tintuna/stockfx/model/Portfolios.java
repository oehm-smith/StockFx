package com.tintuna.stockfx.model;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.persistence.Portfolio;

public class Portfolios {
	ObservableList<Portfolio> portfolios;
	PortfolioLoader loader = null;

	public Portfolios() {
		portfolios = FXCollections.observableArrayList();
		update();
	}

	
	public void update() {
		List<Portfolio> portList = MainApplication.getServiceFactory().getPortfolioService().findAll();
		portfolios.clear();
		portfolios.addAll(portList);
	}

	public ObservableList<Portfolio> getPortfolios() {
		// List<Portfolio> portList = MainApplication.getAppFactory().getPortfolioLoader().loadPortfoliosData();
		return portfolios;
	}

	public void setPortfolios(ObservableList<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

	public void addListener(ListChangeListener listener) {
		portfolios.addListener(listener);
	}

	public void newPortfolio() {
		Portfolio p = new Portfolio("New Portfolio", "Work");
		// portfolios.add(new Portfolio("New Portfolio", "Work"));
		MainApplication.getServiceFactory().getPortfolioService().create(p);
		update();
	}

	public void persist() {
		// MainApplication.getAppFactory().getPortfolioLoader().savePortfolioData(portfolios);
	}

}
