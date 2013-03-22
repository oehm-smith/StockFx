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

public class MainController extends StockFxBorderPaneController {
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

	// public VBox getPortfolioList() {
	// return portfolioList;
	// }
	//
	// public void setPortfolioList(VBox portfolioList) {
	// this.portfolioList = portfolioList;
	// }
	//
	// public GridPane getGridPane() {
	// return gridPane;
	// }
	//
	// public void setGridPane(GridPane gridPane) {
	// this.gridPane = gridPane;
	// }

	public TabPane getTabPane() {
		return tabPane;
	}

	public void setTabPane(TabPane tabPane) {
		this.tabPane = tabPane;
	}

	// @Override
	// public void initialize(URL location, ResourceBundle resources) {
	// initializePrefsButton();
	// }

	/**
	 * Display msg's in the Label field defined in the fxml.
	 * 
	 * @param msg
	 */
	public void setMessage(String msg) {
		messageBox.setText(msg);
	}

	@Override
	protected String getFXML() {
		return "/fxml/Main.fxml";
	}

	@Override
	protected void initializeButtons() {
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

	private void openPreferences() {
		MainApplication.getAppFactory().getTabManager().addTabWithNode(TabStandardNames.Prefs.name(), MainApplication.getAppFactory().getPrefsController(),
				TabManagerParameters.startParams().insertAt(0).openNotAdd(true));
	}

	@Override
	public void loadSelectedEntity() {
		// not needed for this Controller
	}

	@Override
	public void clearCurrentEntity() {
		// not needed for this Controller
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

	@Override
	public void controllerDocumentSelected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerDocumentDeselected() {
		// TODO Auto-generated method stub
		
	}
}
