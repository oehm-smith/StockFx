package com.tintuna.stockfx.model;

import java.util.List;

import com.tintuna.stockfx.application.MainApplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Portfolios {
	ObservableList<Portfolio> portfolios;
	PortfolioLoader loader = null;
	
	public Portfolios() {
		
	}

	public ObservableList<Portfolio> getPortfolios() {
		List<Portfolio> portList = MainApplication.getAppFactory().getPortfolioLoader().loadPortfoliosData();
		portfolios = FXCollections.observableArrayList(portList);
		return portfolios;
	}

	public void setPortfolios(ObservableList<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

	public void newPortfolios() {
		portfolios.add(new Portfolio("New Portfolio", "Work"));
	}
}
