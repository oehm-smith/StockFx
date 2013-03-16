package com.tintuna.stockfx.model;

import java.util.List;

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
import com.tintuna.stockfx.persistence.Portfolio;

public class CollectionModel {
	private static final Logger log = LoggerFactory.getLogger(CollectionModel.class);
	private static CollectionModel collectionInstanece = null;

	private ObservableList<PCollection> collections;
	private PCollection selected = null;
	private StringProperty selectCollectionNameProperty;

	public static CollectionModel instance() {
		if (collectionInstanece == null) {
			collectionInstanece = new CollectionModel();
		}
		return collectionInstanece;
	}

	private CollectionModel() {
		collections = FXCollections.observableArrayList();
		updateCollectionsAll();
	}

	public void updateCollectionList(PCollection c) {
		collections.add(c);
	}

	public void updateCollectionsAll() {
		List<PCollection> cList;
		try {
			cList = MainApplication.getServiceFactory().getCollectionService().findAll();
			collections.clear();
			collections.addAll(cList);
		} catch (StockFxPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ObservableList<PCollection> getCollections() {
		return collections;
	}

	public void setCollections(ObservableList<PCollection> collections) {
		this.collections = collections;
	}

	public void addCollectionsListener(ListChangeListener<PCollection> listener) {
		collections.addListener(listener);
	}

	public PCollection getSelected() {
		return selected;
	}

	public void setSelected(PCollection selected) {
		this.selected = selected;
		setSelectCollectionNamePropertyText(selected.getName());
	}

	// TODO - I don't like having this property separate.  Surely better way to use just the one thing?
	public StringProperty getSelectCollectionNameProperty() {
		if (selectCollectionNameProperty == null) {
			selectCollectionNameProperty = new SimpleStringProperty();
		}
		return selectCollectionNameProperty;
	}

	public void setSelectPCollectionNameProperty(StringProperty selectPCollectionNameProperty) {
		this.selectCollectionNameProperty = selectPCollectionNameProperty;
	}

	public void setSelectCollectionNamePropertyText(String text) {
		getSelectCollectionNameProperty().set(text);
	}

	private ObservableList<Portfolio> getCollectionsPortfolios(PCollection c) {
		ObservableList<Portfolio> p = MainApplication.getModelFactory().getPortfoliosModel(c).getPortfolios();
		System.out.println("-> getPCollectionsStocks - they are:" + p);
		return p;
	}

	public ObservableList<Portfolio> getCollectionsStocksForSelected() {
		if (getSelected() == null) {
			return null;
		}
		System.out.println("-> getPCollectionsStocksForSelected - ...");
		return getCollectionsPortfolios(getSelected());
	}

	public void newCollection(String newCollectionName) throws StockFxPersistenceException {
		PCollection collection = new PCollection(newCollectionName);
		MainApplication.getServiceFactory().getCollectionService().create(collection);
		updateCollectionList(collection);
	}

	public void addPortfolioToSelectedCollection(Portfolio p) throws StockFxPersistenceException, StockFxDuplicateDataException {
		if (getSelected() != null) {
			// EntityManager em = MainApplication.openTransaction();
			// em.getTransaction().begin();
			log.debug("Stock:" + p);
			getSelected().addPortfolio(p);
			MainApplication.getAppFactory().getCrudService().update(getSelected());
			MainApplication.databaseDebugPrintout();
			// MainApplication.endTransaction(em);
		}
	}
}
