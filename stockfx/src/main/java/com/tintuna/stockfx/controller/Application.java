package com.tintuna.stockfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Application implements Initializable {

	@FXML
	private VBox portfolioList;
	@FXML
	private GridPane gridPane;
	@FXML
	private TabPane tabPane;

	private TabManager tabManager;

	public Application() {
	}

	//
	public void initialize(URL arg0, ResourceBundle arg1) {
		tabManager = new TabManager(tabPane);
		FXMLLoader loader = null;
		try
        {
            loader = new FXMLLoader();
            loader.load(getClass().getResourceAsStream("/fxml/Main.fxml"));
        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to load Main.fxml", e);
        }
		Tab mainTab = new Tab("Portfolios");
		mainTab.setContent((Node) loader.getRoot());
		tabPane.getTabs().add(mainTab);

		tabManager.addNewDocument("one", null, null);
		tabManager.addNewDocument("two", null, null);
		tabManager.addNewDocument("three", null, null);
	}
}
