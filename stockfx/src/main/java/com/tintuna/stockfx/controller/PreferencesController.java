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

public class PreferencesController extends BorderPane implements Initializable, Controller {
	private static final Logger log = LoggerFactory.getLogger(PreferencesController.class);

	@FXML
	private Parent root;
	@FXML
	private TextField databaseUrlText;
	@FXML
	private TextField morningstarUsernameText;
	@FXML
	private TextField morningStarPWText;

	public PreferencesController() {
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource("/fxml/Preferences.fxml"));
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
		databaseUrlText.textProperty().bindBidirectional(
				MainApplication.getModelFactory().getStockFxPreferences().getDatabaseURLProperty());
		morningstarUsernameText.textProperty().bind(
				MainApplication.getModelFactory().getStockFxPreferences().getMorningStarUserNameProperty());
		morningStarPWText.textProperty().bind(
				MainApplication.getModelFactory().getStockFxPreferences().getMorningStarPWProperty());
	}

	public void addDBPathLostFocusListener(ChangeListener<Boolean> listener) {
		databaseUrlText.focusedProperty().addListener(listener);
	}
}
