package com.tintuna.stockfx.model;

import java.util.Set;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.exception.StockFxException;
import com.tintuna.stockfx.exception.StockFxPersistenceException;
import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.persistence.PortfolioStock;
import com.tintuna.stockfx.persistence.Stock;

/**
 * Model of the Stocks associated with a particular portfolio. But rather than deal in stocks directly, deal in
 * Portfoliostocks which has a 1:1 with a StockHolding (ie. user's holding) and the Stock which is the raw item (and has
 * price history etc) ie. StockHolding -- Portfoliostock -- Stock
 * 
 * @author bsmith
 */
public class StocksModel {
	private static final Logger log = LoggerFactory.getLogger(StocksModel.class);

	private ObservableList<PortfolioStock> stocks;
	private PortfolioStock selectedStock = null;
	private Portfolio portfolio;

	public StocksModel(Portfolio portfolio) {
		stocks = FXCollections.observableArrayList();
		this.portfolio = portfolio;
		updateStocksAll();
	}

	public void updateStockList(PortfolioStock s) {
		log.debug("updateStockList - PortfolioStock:" + s);
		getStocks();	// todo - this can be removed - its just here to print the debug in getStocks()
		stocks.add(s);
	}

	public void updateStocksAll() {
		// List<Stock> stockList = MainApplication.getServiceFactory().getStockService().findAll();
		stocks.clear();
		stocks.addAll(portfolio.getPortfoliostockCollection());
	}

	public ObservableList<PortfolioStock> getStocks() {
		log.debug(String.format("getStocks - StockTable's items: %s", stocks));

		return stocks;
	}

	public void setStocks(ObservableList<PortfolioStock> stocks) {
		this.stocks = stocks;
	}

	/**
	 * Let the user choose from the available stocks not in the selected portfolio (this is associated with).
	 * 
	 * @return the list of stocks for all available stocks that aren't already in this model.
	 * @throws StockFxPersistenceException
	 */
	public ObservableList<PortfolioStock> getDifferenceStocks() throws StockFxPersistenceException {
		Set<PortfolioStock> allStocks;
		allStocks = new TreeSet<>(MainApplication.getServiceFactory().getPortfoliostockService().findAll());
		log.debug(String.format("All Stocks (# items: %d):%s", allStocks.size(), allStocks));
		log.debug(String.format("Port Stocks (# items: %d):%s", getStocks().size(), getStocks()));
		allStocks.removeAll(getStocks());
		log.debug(String.format("Difference (# items: %d):%s", allStocks.size(), allStocks));
		return FXCollections.observableArrayList(allStocks);
	}

	public void addStocksListener(ListChangeListener<? super PortfolioStock> listener) {
		stocks.addListener(listener);
	}

	public PortfolioStock getSelected() {
		return selectedStock;
	}

	public void setSelected(PortfolioStock selectedStock) {
		log.debug("-> setSelected: " + selectedStock);
		if (selectedStock != null) {
			this.selectedStock = selectedStock;
		}
	}

	// public ObservableList<Portfolio> getStocksPortfolios(Stock s) {
	// System.out.println("-> getStocksStocks - they are:"+s.getobservablePortfoliosThatContainThisStock());
	// return s.getobservablePortfoliosThatContainThisStock();
	// }

	/**
	 * 
	 * @param symbol
	 * @param company
	 * @return the portfolioStock which has a 1:1 with a StockHolding (ie. user's holding) and the Stock which is the
	 *         raw item (and has price history etc) StockHolding -- Portfoliostock -- Stock
	 * @throws StockFxPersistenceException
	 */
	public PortfolioStock newStock(String symbol, String company) throws StockFxPersistenceException {
		Stock s = new Stock(symbol, company);
		PortfolioStock portfolioStock = new PortfolioStock();
		portfolioStock.setStock(s);
		// s.setPortfoliostock(pstock); - don't need to do this as mappedby annotation arg is on the stock
		MainApplication.getServiceFactory().getStockService().create(s);
		MainApplication.getServiceFactory().getPortfoliostockService().create(portfolioStock);
		updateStockList(portfolioStock);
		log.debug(String.format("newStock - %s, %s - portfolioStock: %s - stockList: %s", symbol, company, portfolioStock, getStocks()));
		return portfolioStock;
	}

	public void addStockToSelectedPortfolio(PortfolioStock portfolioStock) throws StockFxPersistenceException {
		Portfolio portfolio = MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected();
		// JPA 'mappedBy' adds the portfolioStock to the Collection on the Portfolio entity
		portfolioStock.setPortfolio(portfolio);
		MainApplication.getServiceFactory().getPortfoliostockService().update(portfolioStock);
		log.debug("addStockToSelectedPortfolio - portfolioStock:" + portfolioStock + ", portfolio:" + portfolio);
	}

	public void updateSelected(String symbol, String company) throws StockFxPersistenceException {
		getSelected().getStock().setSymbol(symbol);
		getSelected().getStock().setCompanyName(company);
		MainApplication.getServiceFactory().getStockService().update(getSelected().getStock());
	}

}
