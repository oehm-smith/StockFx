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

import com.tintuna.stockfx.application.AppFactory;
import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.formatter.StocksComboCellFormatter;
import com.tintuna.stockfx.persistence.Stock;

public class StockController extends BorderPane implements Initializable {
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

	public StockController(AppFactory controllerFactory) {
		this.controllerFactory = controllerFactory;
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
	
	public Parent getRoot() {
		return root;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializePortfolioNameLabel();
		initializeStockCombo();
		initializeAddToPortfolioButton();
		initializeNewStockButton();
	}
	

	private void initializePortfolioNameLabel() {
		portfolioNameLabel.setText(MainApplication.getModelFactory().getPortfolios().getSelected().getName());		
	}

	private void initializeStockCombo() {
		initializestocksComboData();
		stocksCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Stock>() {
			@Override
			public void changed(ObservableValue<? extends Stock> arg0, Stock arg1, Stock arg2) {
				System.out.println("COMBO CHANGED...to:" + arg2.getId() + " = " + arg2.getSymbol());
				MainApplication.getModelFactory().getStocks().setSelected(stocksCombo.getSelectionModel().getSelectedItem());
			}
		});
		stocksCombo.setCellFactory(new Callback<ListView<Stock>,ListCell<Stock>>() {

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
		MainApplication.getModelFactory().getPortfolios().addPortfoliosListener(new ListChangeListener() {

			@SuppressWarnings("rawtypes")
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change c) {
				stocksCombo.setItems(MainApplication.getModelFactory().getStocks().getStocks());
			}

		});
		stocksCombo.setItems(MainApplication.getModelFactory().getStocks().getStocks());
		stocksCombo.getSelectionModel().selectFirst();
	}

	@SuppressWarnings({"unchecked", "rawtypes" })
	private void initializeAddToPortfolioButton() {
		addToPortfolioButton.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				MainApplication.getModelFactory().getPortfolios().addStockToSelectedPortfolio(MainApplication.getModelFactory().getStocks().getSelected());
				// TODO - should use an enum or something rather than 'portfolios'
				MainApplication.getAppFactory().getTabManager().selectTab("portfolios");
			}
			
		});
	}
	
	@SuppressWarnings({"unchecked", "rawtypes" })
	private void initializeNewStockButton() {
		newStockButton.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				String symbol = (String) (!(newStockSymbolText == null || newStockSymbolText.getText().isEmpty()) ? newStockSymbolText.getText() : "BBB");
				String company = (String) (!(newStockCompanyText == null || newStockCompanyText.getText().isEmpty()) ? newStockCompanyText.getText() : "BBB");
				
				Stock s = MainApplication.getModelFactory().getStocks().newStock(symbol,company);
				s.addPortfolio(MainApplication.getModelFactory().getPortfolios().getSelected());
				MainApplication.getModelFactory().getPortfolios().addStockToSelectedPortfolio(s);
				MainApplication.getModelFactory().getStocks().setSelected(s);
				MainApplication.getAppFactory().getTabManager().selectTab("portfolios");
			}
			
		});
	}
	
}
