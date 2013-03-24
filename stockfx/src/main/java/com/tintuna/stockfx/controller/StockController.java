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
import javafx.scene.input.InputEvent;
import javafx.scene.input.InputMethodEvent;
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

	public StockController() {
		log.debug("-> NEW StockController");
	}

	@Override
	protected String getFXML() {
		return "/fxml/Stock.fxml";
	}

	@Override
	protected void initializeButtons() {
		initializeAddToPortfolioButton();
		initializeNewStockButton();
		initializeSaveButton();
		disEnableButtonsForSaveOrNew();
	}

	@Override
	protected void initializeFields() {
		initializePortfolioNameLabel();
		initializeStockCombo();
		initializeNewStockFields();
	}

	/**
	 * Load the selected Stock into the page so it can be viewed / edited.
	 */
	@Override
	public void loadSelectedEntity() {
		PortfolioStock pc = MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().getSelected();
		newStockSymbolText.textProperty().set(pc.getStock().getSymbol());
		newStockCompanyText.textProperty().set(pc.getStock().getCompanyName());
		selectedId = pc.getId();
	}

	/**
	 * The complement of loading the selected is that we want to clear out any existing values for saving a new one.
	 */
	@Override
	public void clearCurrentEntity() {
		newStockSymbolText.textProperty().set("");
		newStockCompanyText.textProperty().set("");
		selectedId = NOTHING_SELECTED_ID;
		// And load up the combo
		initializeStocksComboData();
	}

	private void initializePortfolioNameLabel() {
		portfolioNameLabel.textProperty().bind(MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelectPortfolioNameProperty());
	}

	private void initializeStockCombo() {
		// Handle when item selected in combo list changes
		stocksCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PortfolioStock>() {
			@Override
			public void changed(ObservableValue<? extends PortfolioStock> arg0, PortfolioStock arg1, PortfolioStock arg2) {
				log.debug(String.format("Stock Combo on change - from %s to %s", arg1, arg2));
				MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().setSelected(arg2);// stocksCombo.getSelectionModel().getSelectedItem());
				if (stocksCombo.getSelectionModel().getSelectedIndex() >= 0) {
					addToPortfolioButton.setDisable(false);
				}
				newStockButton.setDisable(true);
				saveButton.setDisable(true);
			}
		});
		// Set how combo list is displayed
		stocksCombo.setCellFactory(new Callback<ListView<PortfolioStock>, ListCell<PortfolioStock>>() {

			@Override
			public ListCell<PortfolioStock> call(ListView<PortfolioStock> arg0) {
				return new StocksComboCellFormatter();
			}
		});
		// Set how the combo when list not showing (it is actually a button) is displayed
		stocksCombo.setButtonCell(new StocksComboCellFormatter());
		// And add the data to it
		// initializeStocksComboData(); - this will happen when loadSelectedEntity() or newEntity() is called
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializeStocksComboData() {
		// This more a change for if and when multiple users so that when one changes the list of stocks, the new list is reflected
		MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().addStocksListener(new ListChangeListener<PortfolioStock>() {
			@SuppressWarnings("rawtypes")
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change c) {
				stocksComboDataPopulate();
				addToPortfolioButton.setDisable(false);
				newStockButton.setDisable(true);
				saveButton.setDisable(true);
			}
		});
		stocksComboDataPopulate();
		selectFirst();

	}

	private void selectFirst() {
		log.debug("selectFirst");
		if (stocksCombo.getItems().size() > 0) {
			log.debug("  > 0 items in combo");
			stocksCombo.getSelectionModel().selectFirst();
			stocksCombo.setValue(stocksCombo.getItems().get(0));
		}
	}

	private void stocksComboDataPopulate() {
		log.debug("-> stocksComboDataPopulate");
		try {
			stocksCombo.setItems(MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().getDifferenceStocks());
		} catch (StockFxPersistenceException e) {
			// TODO - this not actually correct since don't actually know getting from database
			String msg = "Error retrieving PortfolioStock from database - Error #001";
			log.error(msg);
			e.printStackTrace();
			MainApplication.setMessage(msg);
		}
	}

	private void initializeNewStockFields() {
		newStockSymbolText.addEventHandler(InputMethodEvent.ANY, new EventHandler<InputEvent>() {
			@Override
			public void handle(InputEvent event) {
				// All events still seem to be captured
				if (event.getEventType().equals(InputMethodEvent.INPUT_METHOD_TEXT_CHANGED)) {
					log.debug(String.format("newStockSymbolText eventHandler - eventType:%s", event, event.getEventType()));
					disEnableButtonsForSaveOrNew();
				}
			}
		});
		newStockCompanyText.addEventHandler(InputMethodEvent.ANY, new EventHandler<InputEvent>() {
			@Override
			public void handle(InputEvent event) {
				// All events still seem to be captured
				if (event.getEventType().equals(InputMethodEvent.INPUT_METHOD_TEXT_CHANGED)) {
					log.debug(String.format("newStockCompanyText eventHandler - eventType:%s", event, event.getEventType()));
					disEnableButtonsForSaveOrNew();
				}
			}
		});
	}

	protected void disEnableButtonsForSaveOrNew() {
		log.debug(String.format("disEnableButtonsForSaveOrNew - getControllerState():%s", getControllerState()));
		CONTROLLER_STATE cs = getControllerState();
		// If in the 'started' state hide both buttons (they sit over the top of each other so can only show one or the
		// other)
		newStockButton.setDisable(cs != CONTROLLER_STATE.NEW_DOCUMENT);
		newStockButton.setVisible(cs == CONTROLLER_STATE.NEW_DOCUMENT);// && cs != CONTROLLER_STATE.STARTED);
		saveButton.setDisable(cs != CONTROLLER_STATE.OPENED_DOCUMENT);
		saveButton.setVisible(cs == CONTROLLER_STATE.OPENED_DOCUMENT);// || cs != CONTROLLER_STATE.STARTED);

		addToPortfolioButton.setDisable(true);
	}

	// TODO - 19/3/13 - fix this,
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializeAddToPortfolioButton() {
		addToPortfolioButton.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				addStockToSelectedPortfolioPopulate();
				MainApplication.getAppFactory().getTabManager().selectTab(TabStandardNames.Portfolios.name());
			}

		});
		// addToPortfolioButton.setDisable(true);
	}

	private void addStockToSelectedPortfolioPopulate() {
		Portfolio selectedPortfolio = MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected();
		StocksModel stockModel = MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio();
		PortfolioStock selectedPortfolioStock = stockModel.getSelected();
		try {
			stockModel.addStockToSelectedPortfolio(selectedPortfolioStock);
			stockModel.updateStockList(selectedPortfolioStock);
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
			StocksModel selectedStockModel = MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio(); 
			selectedStockModel.addStockToSelectedPortfolio(portfolioStock);
			selectedStockModel.updateStockList(portfolioStock);
			// The JPA mappedBy and cascade policy handles updating the other side of this relationship
			selectedStockModel.setSelected(portfolioStock);
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
			stocksModel.updateSelected(symbol, company);
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
