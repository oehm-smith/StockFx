package com.tintuna.stockfx.model;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.persistence.Stock;

public class Stocks {
	private ObservableList<Stock> stocks;
	private Stock selectedStock = null;

	public Stocks() {
		stocks = FXCollections.observableArrayList();
		updateStocksAll();
	}

	public void updateStockList(Stock s) {
		stocks.add(s);
	}

	public void updateStocksAll() {
		List<Stock> portList = MainApplication.getServiceFactory().getStockService().findAll();
		stocks.clear();
		stocks.addAll(portList);
	}

	public ObservableList<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(ObservableList<Stock> stocks) {
		this.stocks = stocks;
	}

	public void addStocksListener(ListChangeListener listener) {
		stocks.addListener(listener);
	}

	public Stock getSelected() {
		return selectedStock;
	}

	public void setSelected(Stock selectedStock) {
		this.selectedStock = selectedStock;
	}

	public ObservableList<Portfolio> getStocksPortfolios(Stock s) {
		System.out.println("-> getStocksStocks - they are:"+s.getobservablePortfoliosThatContainThisStock());
		return s.getobservablePortfoliosThatContainThisStock();
	}
	
	public Stock newStock(String symbol, String company) {
		Stock s = new Stock(symbol, company);
		MainApplication.getServiceFactory().getStockService().create(s);
		updateStockList(s);
		return s;
	}
}
