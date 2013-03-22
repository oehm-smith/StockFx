package com.tintuna.stockfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.application.TabStandardNames;
import com.tintuna.stockfx.exception.StockFxPersistenceException;
import com.tintuna.stockfx.persistence.PCollection;
import com.tintuna.stockfx.util.StringUtils;

public class CollectionsController extends StockFxBorderPaneController {
	private static final Logger log = LoggerFactory.getLogger(CollectionsController.class);

	@FXML
	private Button newSaveCollectionButton;
	@FXML
	private TextField newCollectionText;

	@Override
	protected String getFXML() {
		return "/fxml/Collection.fxml";
	}

	@Override
	protected void initializeButtons() {
		initializeSaveCollectionButton();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializeSaveCollectionButton() {
		newSaveCollectionButton.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				if (StringUtils.isNotNullEmpty(newCollectionText)) {
					createNewCollection();
				} else {
					MainApplication.setMessage("Field name or type is empty");
				}
			}

		});
	}

	private void createNewCollection() {
		try {
			MainApplication.getModelFactory().getCollectionsModel().newCollection(newCollectionText.getText());
			MainApplication.getAppFactory().getTabManager().selectTab(TabStandardNames.Portfolios.name());
		} catch (StockFxPersistenceException e) {
			String msg = String.format("Error adding new collection with name '%s' - Error #007 - perhaps one with this name and type already exists", newCollectionText.getText());
			log.error(msg);
			e.printStackTrace();
			MainApplication.setMessage(msg);
		}
	}

	/**
	 * Load the selected Collection into the page so it can be viewed / edited.
	 */
	@Override
	public void loadSelectedEntity() {
		PCollection c = MainApplication.getModelFactory().getCollectionsModel().getSelected();
		newCollectionText.textProperty().set(c.getName());
		selectedId = c.getId();
	}

	/**
	 * The complement of loading the selected is that we want to clear out any existing values.
	 */
	@Override
	public void clearCurrentEntity() {
		newCollectionText.textProperty().set("");
		selectedId = NOTHING_SELECTED_ID;
	}

	@Override
	protected void initializeFields() {
		// not needed for this Controller
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
