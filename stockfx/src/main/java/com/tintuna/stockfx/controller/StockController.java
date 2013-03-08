package com.tintuna.stockfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.AppFactory;
import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.exception.StockFxDuplicateDataException;
import com.tintuna.stockfx.exception.StockFxPersistenceException;
import com.tintuna.stockfx.formatter.StocksComboCellFormatter;
import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.persistence.Stock;
import com.tintuna.stockfx.util.StringUtils;

public class StockController extends BorderPane implements Initializable, Controller {
	private static final Logger log = LoggerFactory.getLogger(StockController.class);

	private AppFactory controllerFactory;

	@FXML
	private Parent root;
	@FXML
	private ComboBox<Stock> stocksCombo;
	@FXML
	private Button addToPortfolioButton;
	@FXML
	private Button newStockButton;
	@FXML
	private Label portfolioNameLabel;
	@FXML
	private TextField newStockSymbolText;
	@FXML
	private TextField newStockCompanyText;

	public StockController() {
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource("/fxml/Stock.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException("Unable to load Stock.fxml", e);
		}
	}

	@Override
	public Parent getRoot() {
		return root;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		log.debug("initialize");
		initializePortfolioNameLabel();
		initializeStockCombo();
		initializeAddToPortfolioButton();
		initializeNewStockButton();
	}

	private void initializePortfolioNameLabel() {
		// portfolioNameLabel.setText(MainApplication.getModelFactory().getPortfolios().getSelected().getName());
		portfolioNameLabel.textProperty().bind(MainApplication.getModelFactory().getPortfoliosModel().getSelectPortfolioNameProperty());
	}

	private void initializeStockCombo() {
		initializestocksComboData();
		stocksCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Stock>() {
			@Override
			public void changed(ObservableValue<? extends Stock> arg0, Stock arg1, Stock arg2) {
				MainApplication.getModelFactory().getStocksModel(MainApplication.getModelFactory().getPortfoliosModel().getSelected()).setSelected(stocksCombo.getSelectionModel().getSelectedItem());
			}
		});
		stocksCombo.setCellFactory(new Callback<ListView<Stock>, ListCell<Stock>>() {

			@Override
			public ListCell<Stock> call(ListView<Stock> arg0) {
				// TODO Auto-generated method stub
				return new StocksComboCellFormatter();
			}
		});
		stocksCombo.setButtonCell(new StocksComboCellFormatter());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializestocksComboData() {
		MainApplication.getModelFactory().getPortfoliosModel().addPortfoliosListener(new ListChangeListener() {

			@SuppressWarnings("rawtypes")
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change c) {
				initializestocksComboDataPopulate();
			}

		});
		// stocksCombo.setItems(MainApplication.getModelFactory().getStocksModel(MainApplication.getModelFactory().getPortfoliosModel().getSelected()).getDifferenceStocks());
		initializestocksComboDataPopulate();
		stocksCombo.getSelectionModel().selectFirst();
		Stock selected = null;
		if (stocksCombo.getItems().size() > 0) {
			selected = stocksCombo.getItems().get(0);
		}
		MainApplication.getModelFactory().getStocksModel(MainApplication.getModelFactory().getPortfoliosModel().getSelected()).setSelected(selected);
	}

	private void initializestocksComboDataPopulate() {
		try {
			stocksCombo.setItems(MainApplication.getModelFactory().getStocksModel(MainApplication.getModelFactory().getPortfoliosModel().getSelected()).getDifferenceStocks());
		} catch (StockFxPersistenceException e) {
			String msg = "Error retrieving stocks from database - Error #001";
			log.error(msg);
			e.printStackTrace();
			MainApplication.setMessage(msg);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializeAddToPortfolioButton() {
		addToPortfolioButton.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				Stock selected = MainApplication.getModelFactory().getStocksModel(MainApplication.getModelFactory().getPortfoliosModel().getSelected()).getSelected();
				if (selected != null) {
					addStockToSelectedPortfolioPopulate(selected);
					// TODO - should use an enum or something rather than 'portfolios'
					MainApplication.getAppFactory().getTabManager().selectTab("portfolios");
				}
			}

		});
	}

	private void addStockToSelectedPortfolioPopulate(Stock selected) {
		Portfolio p = MainApplication.getModelFactory().getPortfoliosModel().getSelected();
		try {
			MainApplication.getModelFactory().getPortfoliosModel().addStockToSelectedPortfolio(selected);
		} catch (StockFxPersistenceException e) {
			String msg = String.format("Error adding stock '%s' from database to portfolio '%s' - Error #002", selected, p);
			log.error(msg);
			e.printStackTrace();
			MainApplication.setMessage(msg);
		} catch (StockFxDuplicateDataException e) {
			String msg = String.format("Duplicate stock '%s' added to portfolio '%s'- Error #003", selected, p);
			log.error(msg);
			e.printStackTrace();
			MainApplication.setMessage(msg);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializeNewStockButton() {
		newStockButton.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				initializeNewStockButtonCreateNewStock();
			}

		});
	}

	private void initializeNewStockButtonCreateNewStock() {
		Portfolio portfolio = MainApplication.getModelFactory().getPortfoliosModel().getSelected();
		String symbol = StringUtils.isNotNullEmpty(newStockSymbolText) ? newStockSymbolText.getText() : "";
		String company = StringUtils.isNotNullEmpty(newStockCompanyText) ? newStockCompanyText.getText() : "";
		try {
			Stock s = MainApplication.getModelFactory().getStocksModel(portfolio).newStock(symbol, company);
			s.addPortfolio(MainApplication.getModelFactory().getPortfoliosModel().getSelected());
			MainApplication.getModelFactory().getPortfoliosModel().addStockToSelectedPortfolio(s);
			MainApplication.getModelFactory().getStocksModel(MainApplication.getModelFactory().getPortfoliosModel().getSelected()).setSelected(s);
			MainApplication.getAppFactory().getTabManager().selectTab("portfolios");
		} catch (StockFxPersistenceException e) {
			String msg = String.format("Error adding new stock with symbol '%s' company '%s' - Error #004 - Perhaps this stock already exists", symbol, company, portfolio);
			log.error(msg);
			e.printStackTrace();
			MainApplication.setMessage(msg);
		} catch (StockFxDuplicateDataException e) {
			String msg = String.format("Error duplicate data when adding new stock with symbol '%s' company '%s' - Error #005 - perhaps this portfolio already has this stock", symbol, company, portfolio);
			log.error(msg);
			e.printStackTrace();
			MainApplication.setMessage(msg);
		}
	}

}
