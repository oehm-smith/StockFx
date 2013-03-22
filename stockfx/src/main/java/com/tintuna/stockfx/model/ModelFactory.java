package com.tintuna.stockfx.model;

import com.tintuna.stockfx.persistence.PCollection;
import com.tintuna.stockfx.persistence.Portfolio;

public class ModelFactory {
	private CollectionModel controllers;
	private PortfoliosModel portfolios;
	private StocksModel stocks;

	public CollectionModel getCollectionsModel() {
		return CollectionModel.instance();
	}
	
	/**
	 * A collection consists of 1+ portfolios.
	 * @param collection
	 * @return the Model for the portfolios of the given collection  
	 */
	public PortfoliosModel getPortfoliosModel(PCollection collection) {
		return CollectionAssociatedPortfolios.getStockModelForAssociatedPortfolio(collection);
	}

	/**
	 * 
	 * @return the Model for the portfolios of the selected collection
	 */
	public PortfoliosModel getPortfoliosModelFromSelectedCollection() {
		return getPortfoliosModel(getCollectionsModel().getSelected());
	}
	
	public StocksModel getStocksModel(Portfolio portfolio) {
		return PortfolioAssociatedStocks.getStockModelForAssociatedPortfolio(portfolio);
	}
	
	public StocksModel getStocksModelFromSelectedPortfolio() {
		return getStocksModel(getPortfoliosModelFromSelectedCollection().getSelected());
	}
	public StockFxPreferences getStockFxPreferences() {
		return StockFxPreferences.instance();
	}

}
