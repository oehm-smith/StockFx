package com.tintuna.stockfx.controller;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class DummyController implements Controller {

	@Override
	public Parent getRoot() {
		return new BorderPane();
	}

}
