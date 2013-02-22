package com.tintuna.stockfx.controller;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import com.tintuna.stockfx.model.ModelI;

public class TabManager implements TabI {
	TabPane tabPane;

	public TabManager(TabPane tabpane) {
		this.tabPane = tabpane;
	}

	public TabManager() {
	}

	public void setTabPane(TabPane tabPane) {
		this.tabPane=tabPane;
	}
	
	private TabPane getTabPane() {
		if (tabPane == null) {
			throw new RuntimeException("TabPane needs to be set with setTabPane(TabPane)");
		}
		return tabPane;
	}
	@Override
	public void addNewDocument(String name, String type, ModelI model) {
		getTabPane().getTabs().add(new Tab(name));
	}

	@Override
	public void addNewDocument(String name, Node node) {
		Tab tab = new Tab(name);
		tab.setContent(node);
		getTabPane().getTabs().add(tab);
	}
}
