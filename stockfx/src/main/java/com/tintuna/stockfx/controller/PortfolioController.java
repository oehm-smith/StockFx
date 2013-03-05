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

import com.tintuna.stockfx.application.AppFactory;
import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.util.StringUtils;

public class PortfolioController extends BorderPane implements Initializable, Controller {
	private AppFactory controllerFactory;

	@FXML
	private Parent root;
	@FXML
	private Button newSavePortfolioButton;
	@FXML
	private TextField newPortfolioText;
	@FXML
	private TextField newTypeText;

	public PortfolioController(AppFactory controllerFactory) {
		this.controllerFactory = controllerFactory;
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource("/fxml/Portfolio.fxml"));
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
		initializeSavePortfolioButton();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializeSavePortfolioButton() {
		newSavePortfolioButton.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				if (StringUtils.isNotNullEmpty(newPortfolioText) && StringUtils.isNotNullEmpty(newTypeText)) {
					MainApplication.getModelFactory().getPortfoliosModel()
							.newPortfolio(newPortfolioText.getText(), newTypeText.getText());
					// TODO - should use an enum or something rather than 'portfolios'
					MainApplication.getAppFactory().getTabManager().selectTab("portfolios");
				}
			}

		});
	}
}
