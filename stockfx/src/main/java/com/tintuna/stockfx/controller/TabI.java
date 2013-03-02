package com.tintuna.stockfx.controller;

import javafx.scene.Node;

import com.tintuna.stockfx.util.TabManagerParameters;

public interface TabI {
	/**
	 * Add a new Tab to the TabPane and set the contents to the given node.
	 * 
	 * @param tabName of the tab which we can refer to later
	 * @param node to populate as the contents of the tab
	 */
	public void addTabWithNode(String tabName, Node node);

	/**
	 * Add a new Tab to the TabPane and set the contents to the given node. Use parameters to specify its placement or
	 * if to add a suffix as per {@link com.tintuna.stockfx.util.TabManagerParameters}.
	 * 
	 * @param tabName of the tab which we can refer to later
	 * @param node to populate as the contents of the tab
	 * @param params for where to place the new tab relative to existing tabs or absolutely and if to give a suffix to
	 *            make the name unique (names must be unique).
	 */
	public void addTabWithNode(String tabName, Node root, TabManagerParameters params);

}
