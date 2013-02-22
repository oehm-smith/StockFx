package com.tintuna.stockfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import com.tintuna.stockfx.model.Portfolio;

public class PortfolioController extends BorderPane implements Initializable {
	private AppFactory controllerFactory;
	@FXML
	private Parent root;
	@FXML
	private VBox portfolioList;
	@FXML
	private TableView<Portfolio> portfolioTable;

	@FXML
	private GridPane gridPane;
	@FXML
	private TabPane tabPane;

	public PortfolioController(AppFactory controllerFactory) {
		this.controllerFactory = controllerFactory;
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource("/fxml/Portfolio.fxml"));
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

	public TableView<Portfolio> getPortfolioTable() {
		return portfolioTable;
	}

	public void setPortfolioTable(TableView<Portfolio> portfolioTable) {
		this.portfolioTable = portfolioTable;
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
		// adding something to see
		TableColumn<Portfolio, String> col = new TableColumn<>("Portfolio");
		portfolioTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		col.setCellValueFactory(new PropertyValueFactory<Portfolio,String>("Name"));
		final ObservableList<Portfolio> data = FXCollections.observableArrayList();//new Portfolio("Portfolio 1", "Home"),new Portfolio("Portfolio 2", "Home"));
		for (Integer i : new int[] { 1, 2, 3 }) {
			data.add(new Portfolio("Portfolio " + i.toString(), "Home"));
		}
		portfolioTable.setItems(data);
		portfolioTable.getColumns().clear();
		portfolioTable.getColumns().add(col);
	}

}
