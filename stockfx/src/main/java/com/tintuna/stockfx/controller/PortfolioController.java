package com.tintuna.stockfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.AppFactory;
import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.application.TabStandardNames;
import com.tintuna.stockfx.exception.StockFxPersistenceException;
import com.tintuna.stockfx.persistence.PType;
import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.util.StringUtils;

public class PortfolioController extends StockFxBorderPaneController {
	private static final Logger log = LoggerFactory.getLogger(PortfolioController.class);
	// private AppFactory controllerFactory;

	@FXML
	private Parent root;
	@FXML
	private Button newSavePortfolioButton;
	@FXML
	private TextField newPortfolioText;
	@FXML
	private ListView<PType> typeList;

	@Override
	protected String getFXML() {
		return "/fxml/Portfolio.fxml";
	}

	@Override
	protected void initializeButtons() {
		initializeSavePortfolioButton();
	}

	@Override
	protected void initializeFields() {
		initializeTypeList();
	}

	@Override
	public void loadSelectedEntity() {
		Portfolio p = MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected();
		log.debug("-> loadSelected - selected portfolio:" + p);
		if (p != null) {
			newPortfolioText.textProperty().set(p.getName());
			typeList.getSelectionModel().select(p.getPortfolioTypeid());
			setControllerState(CONTROLLER_STATE.OPENED_DOCUMENT);
		}
	}

	@Override
	public void clearCurrentEntity() {
		log.debug("-> clearCurrentEntity");
		newPortfolioText.textProperty().set("");
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
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializeSavePortfolioButton() {
		newSavePortfolioButton.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				if (StringUtils.isNotNullEmpty(newPortfolioText)) {
					newEntity();
				} else {
					MainApplication.setMessage("Field name or type is empty");
				}
			}

		});
	}

	@Override
	public void newEntity() {
		PType pt = typeList.selectionModelProperty().get().getSelectedItem();
		try {
			MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().newPortfolio(newPortfolioText.getText(), pt);
			MainApplication.getAppFactory().getTabManager().selectTab(TabStandardNames.Portfolios.name());
			setControllerState(CONTROLLER_STATE.NEW_DOCUMENT);
		} catch (StockFxPersistenceException e) {
			String msg =
					String.format("Error adding new portfolio with name '%s' and type '%s' - Error #006 - perhaps one with this name and type already exists", newPortfolioText.getText(),
							pt.toString());
			log.error(msg);
			e.printStackTrace();
			MainApplication.setMessage(msg);
		}
	}

	private void initializeTypeList() {
		typeList.getItems().setAll(Arrays.asList(PType.values()));
	}

	@Override
	public void deleteSelectedEntity() {
		// TODO Auto-generated method stub

	}
}
