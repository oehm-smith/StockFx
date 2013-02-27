package com.tintuna.stockfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import com.tintuna.stockfx.application.AppFactory;
import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.persistence.Stock;

public class PortfolioController extends BorderPane implements Initializable {
	private AppFactory controllerFactory;
	@FXML
	private Parent root;
	@FXML
	private VBox portfolioList;
	@FXML
	private TableView<Portfolio> portfolioTable;
	@FXML
	private TableView<Stock> stockTable;

	@FXML
	private GridPane gridPane;
	@FXML
	private TabPane tabPane;
	@FXML
	private Button newPortfolioButton;
	@FXML
	private Button newStockButton;
	private Portfolio selected = null;
	private Stock selectedStock = null;

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
		initializePortfolioTable();
		initializeStockTable();
		initializeNewPortfolioButton();
		initializeNewStockButton();
	}

	private void initializePortfolioTable() {
		TableColumn<Portfolio, String> col = new TableColumn<>("Portfolio");
		portfolioTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		col.setCellValueFactory(new PropertyValueFactory<Portfolio, String>("Name"));
		col.setText("Name");
		initializePortfolioTableData();
		portfolioTable.getColumns().clear();
		portfolioTable.getColumns().add(col);
		portfolioTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Portfolio>() {
			@Override
			public void changed(ObservableValue<? extends Portfolio> arg0, Portfolio arg1, Portfolio arg2) {
				System.out.println("CHANGED...to:" + arg2.getId() + " = " + arg2.getName());
				if (arg2 != null && arg2 instanceof Portfolio) {
					portfolioSelected(arg2);
				}
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializePortfolioTableData() {
		MainApplication.getModelFactory().getPortfolios().addPortfoliosListener(new ListChangeListener() {

			@SuppressWarnings("rawtypes")
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change c) {
				portfolioTable.setItems(MainApplication.getModelFactory().getPortfolios().getPortfolios());
			}

		});
		portfolioTable.setItems(MainApplication.getModelFactory().getPortfolios().getPortfolios());
	}

	private void initializeStockTable() {
		// adding something to see
		stockTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<Stock, String> colSymbol = new TableColumn<>("Symbol");
		colSymbol.setCellValueFactory(new PropertyValueFactory<Stock, String>("Symbol"));
		colSymbol.setText("Symbol");
		TableColumn<Stock, String> colCompany = new TableColumn<>("CompanyName");
		colCompany.setCellValueFactory(new PropertyValueFactory<Stock, String>("CompanyName"));
		colCompany.setText("CompanyName");
		initializeStockTableData();
		stockTable.getColumns().clear();
		stockTable.getColumns().addAll(colSymbol,colCompany);
		stockTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Stock>() {
			@Override
			public void changed(ObservableValue<? extends Stock> arg0, Stock arg1, Stock arg2) {
				if (arg2 != null) {
				System.out.println("Stock CHANGED...to:" + arg2.getId() + " = " + arg2.getSymbol());
				} else {
					System.out.println("Stock CHANGED...to: well arg2 is null");
				}
				if (arg2 != null && arg2 instanceof Stock) {
					stockSelected(arg2);
				}
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializeStockTableData() {
		MainApplication.getModelFactory().getPortfolios().addPortfoliosListener(new ListChangeListener() {

			@SuppressWarnings("rawtypes")
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change c) {
				System.out.println("onChanged table data");
				setStockItems();
			}
		});
		setStockItems();
	}
	
	private void setStockItems() {
		if (selected != null) {
			stockTable.setItems(MainApplication.getModelFactory().getPortfolios().getPortfoliosStocks(selected));
		}		
	}

	private void initializeNewPortfolioButton() {
		newPortfolioButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				MainApplication.getModelFactory().getPortfolios().newPortfolio();
			}
		});
	}

	private void initializeNewStockButton() {
		newStockButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				newStockForPortfolio();
			}
		});
		newStockButton.setDisable(true);
	}

	protected void newStockForPortfolio() {
		if (selected == null)
			return;
		// Insert dummy test data
		Integer a = (int) (Math.random() * 1000);
		Stock s = new Stock(a.toString(), "Random Bank");
		s.addPortfolio(selected);
		MainApplication.getServiceFactory().getStockService().create(s);

		selected.addStock(s);
	}

	protected void portfolioSelected(Portfolio portfolio) {
		newStockButton.setDisable(false);
		this.selected = portfolio;
		setStockItems();
	}

	protected void stockSelected(Stock stock) {
		this.selectedStock = stock;
	}

}
