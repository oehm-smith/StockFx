package com.tintuna.stockfx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ApplicationC implements Initializable {

	@FXML
	private VBox portfolioList;
	@FXML
	private GridPane gridPane;

	public ApplicationC() {
	}

	//
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Scroll Pane
		List<String> strList = new ArrayList<>();
		ObservableList<String> contents = FXCollections.observableList(strList);
		ListView<String> listx = new ListView<>(contents);
		portfolioList.getChildren().add(listx);
		VBox.setVgrow(listx, Priority.ALWAYS);

		for (int i = 0; i < 20; i++) {
			String x = Integer.toString(i);
			// portfolioList.setVgrow(x, Priority.ALWAYS);

			strList.add(x);
		}

		// GridPane
		for (int i = 0; i < 20; i++) {
			Text name = new Text("Row " + Integer.toString(i));
			Text data = new Text("Data " + Integer.toString(i));
			name.setStyle("-fx-font: 10px Tahoma; -fx-fill:blue");
			gridPane.add(name, 0, i);
			gridPane.add(data, 1, i);
			GridPane.setHalignment(name, HPos.RIGHT);
			GridPane.setHalignment(data, HPos.LEFT);
		}
	}
}
