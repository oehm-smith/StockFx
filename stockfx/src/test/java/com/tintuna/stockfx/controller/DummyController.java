package com.tintuna.stockfx.controller;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class DummyController extends StockFxBorderPaneController {

	@Override
	public Parent getRoot() {
		return new BorderPane();
	}

	@Override
	protected String getFXML() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initializeButtons() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initializeFields() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadSelectedEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearCurrentEntity() {
		// TODO Auto-generated method stub
		
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
