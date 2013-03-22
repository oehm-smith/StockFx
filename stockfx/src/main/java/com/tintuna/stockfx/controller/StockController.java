package com.tintuna.stockfx.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.application.TabStandardNames;
import com.tintuna.stockfx.exception.StockFxPersistenceException;
import com.tintuna.stockfx.formatter.StocksComboCellFormatter;
import com.tintuna.stockfx.model.StocksModel;
import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.persistence.PortfolioStock;
import com.tintuna.stockfx.util.StringUtils;

public class StockController extends StockFxBorderPaneController {
	private static final Logger log = LoggerFactory.getLogger(StockController.class);

	@FXML
	private ComboBox<PortfolioStock> stocksCombo;
	@FXML
	private Button addToPortfolioButton;
	@FXML
	private Button newStockButton;
	@FXML
	private Button saveButton;
	@FXML
	private Label portfolioNameLabel;
	@FXML
	private TextField newStockSymbolText;
	@FXML
	private TextField newStockCompanyText;

	@Override
	protected String getFXML() {
		return "/fxml/Stock.fxml";
	}

	@Override
	protected void initializeButtons() {
		initializeAddToPortfolioButton();
		initializeNewStockButton();
		initializeSaveButton();
	}

	@Override
	protected void initializeFields() {
		initializePortfolioNameLabel();
		initializeStockCombo();
	}

	/**
	 * Load the selected Stock into the page so it can be viewed / edited.
	 */
	@Override
	public void loadSelectedEntity() {
		PortfolioStock pc = MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().getSelected();
		newStockSymbolText.textProperty().set(pc.getStockid().getSymbol());
		newStockCompanyText.textProperty().set(pc.getStockid().getCompanyName());
		selectedId = pc.getId();
	}

	/**
	 * The complement of loading the selected is that we want to clear out any existing values.
	 */
	@Override
	public void clearCurrentEntity() {
		newStockSymbolText.textProperty().set("");
		newStockCompanyText.textProperty().set("");
		selectedId = NOTHING_SELECTED_ID;
	}

	private void initializePortfolioNameLabel() {
		// portfolioNameLabel.setText(MainApplication.getModelFactory().getPortfolios().getSelected().getName());
		portfolioNameLabel.textProperty().bind(MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelectPortfolioNameProperty());
	}

	private void initializeStockCombo() {
		initializestocksComboData();
		stocksCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PortfolioStock>() {
			@Override
			public void changed(ObservableValue<? extends PortfolioStock> arg0, PortfolioStock arg1, PortfolioStock arg2) {
				MainApplication.getModelFactory().getStocksModel(MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected()).setSelected(
						stocksCombo.getSelectionModel().getSelectedItem());
			}
		});
		stocksCombo.setCellFactory(new Callback<ListView<PortfolioStock>, ListCell<PortfolioStock>>() {

			@Override
			public ListCell<PortfolioStock> call(ListView<PortfolioStock> arg0) {
				// TODO Auto-generated method stub
				return new StocksComboCellFormatter();
			}
		});
		stocksCombo.setButtonCell(new StocksComboCellFormatter());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializestocksComboData() {
		MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().addPortfoliosListener(new ListChangeListener() {

			@SuppressWarnings("rawtypes")
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change c) {
				stocksComboDataPopulate();
			}

		});
		// stocksCombo.setItems(MainApplication.getModelFactory().getStocksModel(MainApplication.getModelFactory().getPortfoliosModel().getSelected()).getDifferenceStocks());
		stocksComboDataPopulate();
		stocksCombo.getSelectionModel().selectFirst();
		PortfolioStock selected = null;
		if (stocksCombo.getItems().size() > 0) {
			selected = stocksCombo.getItems().get(0);
		}
		// TODO - this isn't doing what Im expecting - to highlight the first one in the list, or is it to highlight the
		// one that IS THE SELECTED
		// MainApplication.getModelFactory().getStocksModel(MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected()).setSelected(selected);
	}

	private void stocksComboDataPopulate() {
		try {
			stocksCombo.setItems(MainApplication.getModelFactory().getStocksModel(MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected()).getDifferenceStocks());
		} catch (StockFxPersistenceException e) {
			// TODO - this not actually correct since don't actually know getting from database
			String msg = "Error retrieving PortfolioStock from database - Error #001";
			log.error(msg);
			e.printStackTrace();
			MainApplication.setMessage(msg);
		}
	}

	// TODO - 19/3/13 - fix this,
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializeAddToPortfolioButton() {
		addToPortfolioButton.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				addStockToSelectedPortfolioPopulate();
				MainApplication.getAppFactory().getTabManager().selectTab(TabStandardNames.Portfolio.name());
			}

		});
	}

	private void addStockToSelectedPortfolioPopulate() {
		PortfolioStock selectedPortfolioStock = MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().getSelected();
		Portfolio selectedPortfolio = MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected();

		StocksModel stockModel = MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio();
		try {
			stockModel.addStockToSelectedPortfolio(selectedPortfolioStock);
		} catch (StockFxPersistenceException e) {
			String msg = String.format("Error adding stock '%s' from database to portfolio '%s' - Error #002", selectedPortfolioStock, selectedPortfolio);
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
				createNewStock();
			}

		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializeSaveButton() {
		saveButton.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				// check if new or open state to change New/Save behaviour
				saveStock();
			}

		});
	}

	private void createNewStock() {
		Portfolio portfolio = MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected();
		String symbol = StringUtils.isNotNullEmpty(newStockSymbolText) ? newStockSymbolText.getText() : "";
		String company = StringUtils.isNotNullEmpty(newStockCompanyText) ? newStockCompanyText.getText() : "";

		try {
			PortfolioStock portfolioStock = MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().newStock(symbol, company);
			MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().addStockToSelectedPortfolio(portfolioStock);
			// portfolioStock.setPortfolioid(MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected());
			// Pretty sure don't need addStockToSelectedPortfolio() as the JPA mappedBy handles that
			// MainApplication.getModelFactory().getPortfoliosModel().addStockToSelectedPortfolio(portfolioStock);
			MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().setSelected(portfolioStock);
			MainApplication.getAppFactory().getTabManager().selectTab(TabStandardNames.Portfolios.name());
		} catch (StockFxPersistenceException e) {
			String msg = String.format("Error adding new stock with symbol '%s' company '%s' - Error #004 - Perhaps this stock already exists", symbol, company, portfolio);
			log.error(msg);
			e.printStackTrace();
			MainApplication.setMessage(msg);
		}
	}

	private void saveStock() {
		String symbol = StringUtils.isNotNullEmpty(newStockSymbolText) ? newStockSymbolText.getText() : "";
		String company = StringUtils.isNotNullEmpty(newStockCompanyText) ? newStockCompanyText.getText() : "";
		StocksModel stocksModel = MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio();
		try {
			stocksModel.updateSelected(symbol,company);
			MainApplication.getAppFactory().getTabManager().selectTab(TabStandardNames.Portfolios.name());
		} catch (StockFxPersistenceException e) {
			Portfolio portfolio = MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected();
			String msg = String.format("Error adding new stock with symbol '%s' company '%s' - Error #004 - Perhaps this stock already exists", symbol, company, portfolio);
			log.error(msg);
			e.printStackTrace();
			MainApplication.setMessage(msg);
		}
	}

	@Override
	public void newEntity() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSelectedEntity() {
		// TODO Auto-generated method stub

	}

	/**
	 * If the controller is for NEW DOCUMENTS then want to clear all fields. Although the fields would typically already
	 * be blank, if the TAB is selected we want to use this controller to be able to create further NEW portfolio
	 * entities. Thus clearing the contents is appropriate. However if the fields are currently holding an existing
	 * Portfolio Entity for view / editing we of course don't want to clear the fields then.
	 */
	@Override
	public void controllerDocumentSelected() {
		switch (getControllerState()) {
		case OPENED_DOCUMENT:
			loadSelectedEntity();
			break;
		default:
			clearCurrentEntity();
		}
	}

	@Override
	public void controllerDocumentDeselected() {
		// TODO Auto-generated method stub

	}
}
