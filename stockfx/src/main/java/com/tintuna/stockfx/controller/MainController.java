package com.tintuna.stockfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import com.tintuna.stockfx.application.AppFactory;
import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.application.TabStandardNames;
import com.tintuna.stockfx.util.TabManagerParameters;

public class MainController extends BorderPane implements Initializable {
	private AppFactory controllerFactory;
	@FXML
	private Parent root;
	@FXML
	private VBox portfolioList;
	@FXML
	private GridPane gridPane;
	@FXML
	private TabPane tabPane;
	@FXML
	private Button preferencesButton;
	@FXML
	private Label messageBox;

	public MainController(AppFactory controllerFactory) {
		this.controllerFactory = controllerFactory;
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException("Unable to load Main.fxml", e);
		}
	}

	public Parent getRoot() {
		return root;
	}

	public VBox getPortfolioList() {
		return portfolioList;
	}

	public void setPortfolioList(VBox portfolioList) {
		this.portfolioList = portfolioList;
	}

	public GridPane getGridPane() {
		return gridPane;
	}

	public void setGridPane(GridPane gridPane) {
		this.gridPane = gridPane;
	}

	public TabPane getTabPane() {
		return tabPane;
	}

	public void setTabPane(TabPane tabPane) {
		this.tabPane = tabPane;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializePrefsButton();
	}

	private void initializePrefsButton() {
		preferencesButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				openPreferences();
			}
		});
		preferencesButton.setDisable(false);
	}

	protected void openPreferences() {
		MainApplication
				.getAppFactory()
				.getTabManager()
				.addTabWithNode(TabStandardNames.Prefs.name(), MainApplication.getAppFactory().getPrefsController(),
						TabManagerParameters.startParams().insertAt(0).openNotAdd(true));
	}

	public void setMessage(String msg) {
		messageBox.setText(msg);
	}
}
