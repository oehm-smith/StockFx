package com.tintuna.stockfx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.exception.StockFxDuplicateDataException;
import com.tintuna.stockfx.exception.StockFxPersistenceException;
import com.tintuna.stockfx.persistence.PCollection;
import com.tintuna.stockfx.persistence.PType;
import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.persistence.PortfolioStock;

public class PortfoliosModel {
	private static final Logger log = LoggerFactory.getLogger(PortfoliosModel.class);
	private PCollection collection;
	private ObservableList<Portfolio> portfolios;
	private Portfolio selected = null;
	private StringProperty selectPortfolioNameProperty;

	public PortfoliosModel(PCollection collection) {
		this.collection = collection;
		portfolios = FXCollections.observableArrayList();
		updatePortfoliosAll();
	}

	public void updatePortfolioList(Portfolio p) {
		portfolios.add(p);
	}

	public void updatePortfoliosAll() {
		portfolios.clear();
		portfolios.addAll(collection.getPortfolioCollection());
	}

	public ObservableList<Portfolio> getPortfolios() {
		return portfolios;
	}

	public void setPortfolios(ObservableList<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

	public void addPortfoliosListener(ListChangeListener<Portfolio> listener) {
		portfolios.addListener(listener);
	}

	public Portfolio getSelected() {
		return selected;
	}

	public void setSelected(Portfolio selected) {
		this.selected = selected;
		if (selected == null) {
			setSelectPortfolioNamePropertyText("");
		} else {
			setSelectPortfolioNamePropertyText(selected.getName());
		}
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

	private ObservableList<PortfolioStock> getPortfoliosStocks(Portfolio p) {
		ObservableList<PortfolioStock> sList = MainApplication.getModelFactory().getStocksModel(p).getStocks();
		System.out.println("-> getPCollectionsStocks - they are:" + sList);
		return sList;
	}

	public ObservableList<PortfolioStock> getPortfoliosStocksForSelected() {
		if (getSelected() == null) {
			return null;
		}
		System.out.println("-> getPCollectionsStocksForSelected - ...");
		return getPortfoliosStocks(getSelected());
	}

	// public ObservableList<Stock> getPortfoliosStocks(Portfolio p) {
	// System.out.println("-> getPortfoliosStocks - they are:" + p.getobservableStocksInThisPortfolio());
	// return p.getobservableStocksInThisPortfolio();
	// }

	// MOVE TO PortfolioAssociatedStock using lookup in map
	// public ObservableList<Stock> getPortfoliosStocksForSelected() {
	// if (getSelected() == null) {
	// return null;
	// }
	// System.out.println("-> getPortfoliosStocksForSelected - they are:" +
	// getSelected().getobservableStocksInThisPortfolio());
	// return getSelected().getobservableStocksInThisPortfolio();
	// }

	/**
	 * Create a new portfolio with the selected collection as its parent collection and persist.
	 * @param newPortfolioName
	 * @param type
	 * @return the portfolio that is created
	 * @throws StockFxPersistenceException
	 */
	public Portfolio newPortfolio(String newPortfolioName, PType type) throws StockFxPersistenceException {
		Portfolio portfolio = new Portfolio(newPortfolioName, type);
		portfolio.setCollection(MainApplication.getModelFactory().getCollectionsModel().getSelected());
		MainApplication.getServiceFactory().getPortfolioService().create(portfolio);
		updatePortfolioList(portfolio);
		return portfolio;
	}

//	public void addStockToSelectedPortfolio(PortfolioStock stock) throws StockFxPersistenceException, StockFxDuplicateDataException {
//		if (getSelected() != null) {
//			// EntityManager em = MainApplication.openTransaction();
//			// em.getTransaction().begin();
//			log.debug("Stock:" + stock);
//			getSelected().addPortfolioStock(stock);
//			MainApplication.getAppFactory().getCrudService().update(getSelected());
//			MainApplication.databaseDebugPrintout();
//			// MainApplication.endTransaction(em);
//		}
//	}
}
