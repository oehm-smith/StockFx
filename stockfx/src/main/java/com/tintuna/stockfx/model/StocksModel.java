package com.tintuna.stockfx.model;

import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.exception.StockFxPersistenceException;
import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.persistence.Stock;

/**
 * Model of the Stocks associated with a particular portfolio.
 * 
 * @author bsmith
 */
public class StocksModel {
	private static final Logger log = LoggerFactory.getLogger(MainApplication.class);

	private ObservableList<Stock> stocks;
	private Stock selectedStock = null;
	private Portfolio portfolio;

	public StocksModel(Portfolio portfolio) {
		stocks = FXCollections.observableArrayList();
		this.portfolio = portfolio;
		updateStocksAll();
	}

	public void updateStockList(Stock s) {
		stocks.add(s);
	}

	public void updateStocksAll() {
		// List<Stock> stockList = MainApplication.getServiceFactory().getStockService().findAll();
		stocks.clear();
		stocks.addAll(portfolio.getobservableStocksInThisPortfolio());
	}

	public ObservableList<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(ObservableList<Stock> stocks) {
		this.stocks = stocks;
	}

	/**
	 * Let the user choose from the available stocks not in the selected portfolio (this is associated with).
	 * 
	 * @return the list of stocks for all available stocks that aren't already in this model.
	 * @throws StockFxPersistenceException 
	 */
	public ObservableList<Stock> getDifferenceStocks() throws StockFxPersistenceException {
		Set<Stock> allStocks;
			allStocks = new TreeSet<>(MainApplication.getServiceFactory().getStockService().findAll());
		log.debug("All Stocks:" + allStocks);
		log.debug("Port Stocks:" + getStocks());
		allStocks.removeAll(getStocks());
		log.debug("Difference:" + allStocks);
		return FXCollections.observableArrayList(allStocks);
	}

	public void addStocksListener(ListChangeListener<? super Stock> listener) {
		stocks.addListener(listener);
	}

	public Stock getSelected() {
		return selectedStock;
	}

	public void setSelected(Stock selectedStock) {
		this.selectedStock = selectedStock;
	}

	// public ObservableList<Portfolio> getStocksPortfolios(Stock s) {
	// System.out.println("-> getStocksStocks - they are:"+s.getobservablePortfoliosThatContainThisStock());
	// return s.getobservablePortfoliosThatContainThisStock();
	// }

	public Stock newStock(String symbol, String company) throws StockFxPersistenceException {
		Stock s = new Stock(symbol, company);
		MainApplication.getServiceFactory().getStockService().create(s);
		updateStockList(s);
		return s;
	}

}
