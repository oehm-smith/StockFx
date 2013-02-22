package com.tintuna.stockfx.controller;

import javafx.scene.Node;

import com.tintuna.stockfx.model.ModelI;

public interface TabI {
	public void addNewDocument(String name, String type, ModelI model);
	public void addNewDocument(String name, Node node);
}
