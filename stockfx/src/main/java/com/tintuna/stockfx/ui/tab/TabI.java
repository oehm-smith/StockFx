package com.tintuna.stockfx.ui.tab;

import javafx.scene.Node;

import com.tintuna.stockfx.controller.StockFxBorderPaneController;
import com.tintuna.stockfx.util.TabManagerParameters;

public interface TabI {
	/**
	 * Add a new Tab to the TabPane and set the contents to the given node.
	 * 
	 * @param tabName of the tab which we can refer to later
	 * @param controller to populate as the contents of the tab (ie. controller.getRoot())
	 * @param node to populate as the contents of the tab
	 */
	public void addTabWithNode(String tabName, StockFxBorderPaneController controller);

	/**
	 * Add a new Tab to the TabPane and set the contents to the given node. Use parameters to specify its placement or
	 * if to add a suffix as per {@link com.tintuna.stockfx.util.TabManagerParameters}.
	 * 
	 * @param tabName of the tab which we can refer to later
	 * @param controller to populate as the contents of the tab (ie. controller.getRoot())
	 * @param params for where to place the new tab relative to existing tabs or absolutely and if to give a suffix to
	 *            make the name unique (names must be unique).
	 */
	void addTabWithNode(String tabName, StockFxBorderPaneController controller, TabManagerParameters params);
}
