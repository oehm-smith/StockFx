package com.tintuna.stockfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.MainApplication;

public class PreferencesController extends StockFxBorderPaneController implements PrefsController {
	private static final Logger log = LoggerFactory.getLogger(PreferencesController.class);

	@FXML
	private Parent root;
	@FXML
	private TextField databaseUrlText;
	@FXML
	private TextField morningstarUsernameText;
	@FXML
	private TextField morningStarPWText;

	public void addDBPathLostFocusListener(ChangeListener<Boolean> listener) {
		databaseUrlText.focusedProperty().addListener(listener);
	}

	@Override
	protected String getFXML() {
		return "/fxml/Preferences.fxml";
	}

	@Override
	protected void initializeButtons() {
		// none to define
	}

	@Override
	public void loadSelectedEntity() {
		// not relevant here
	}

	@Override
	public void clearCurrentEntity() {
		// not relevant here
	}

	@Override
	protected void initializeFields() {
		databaseUrlText.textProperty().bindBidirectional(
				MainApplication.getModelFactory().getStockFxPreferences().getDatabaseURLProperty());
		morningstarUsernameText.textProperty().bind(
				MainApplication.getModelFactory().getStockFxPreferences().getMorningStarUserNameProperty());
		morningStarPWText.textProperty().bind(
				MainApplication.getModelFactory().getStockFxPreferences().getMorningStarPWProperty());
	}

	@Override
	public void newEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSelectedEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerDocumentSelected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerDocumentDeselected() {
		// TODO Auto-generated method stub
		
	}
}
