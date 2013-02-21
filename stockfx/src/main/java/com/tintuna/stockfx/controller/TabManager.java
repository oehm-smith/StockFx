package com.tintuna.stockfx.controller;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import com.tintuna.stockfx.model.ModelI;

public class TabManager implements TabI {
	TabPane tabPane;

	public TabManager(TabPane tabpane) {
		this.tabPane = tabpane;
	}

	private TabManager() {
		// force use of constructor with arg
	}

	@Override
	public void addNewDocument(String name, String type, ModelI model) {
		System.out.println("TabPane:"+tabPane);
		tabPane.getTabs().add(new Tab(name));
	}
}
