package com.tintuna.stockfx.controller;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import com.tintuna.stockfx.exception.StockFxException;
import com.tintuna.stockfx.util.TabManagerParameters;

public class TabManager implements TabI {
	private TabPane tabPane;
	/**
	 * Keep track of what is in what tab so can insert tabs before / after existing ones
	 */
	private Map<String, Integer> tabNames;

	public TabManager(TabPane tabpane) {
		this();
		this.tabPane = tabpane;
	}

	public TabManager() {
		tabNames = new HashMap<>();
	}

	public void setTabPane(TabPane tabPane) {
		this.tabPane = tabPane;
	}

	protected TabPane getTabPane() {
		if (tabPane == null) {
			throw new RuntimeException("TabPane needs to be set with setTabPane(TabPane)");
		}
		return tabPane;
	}

	protected Map<String, Integer> getTabNames() {
		return tabNames;
	}

	private void setTabNames(Map<String, Integer> tabs) {
		this.tabNames = tabs;
	}

	/**
	 * @throws StockFxException if tab with name already exists
	 * @param name
	 * @param index
	 */
	private void addTabNameMapEntry(String name, int index) {
		if (tabNames.containsKey(name.toLowerCase())) {
			throw new StockFxException(String.format("Tab with name %s already exists", name));
		}
		tabNames.put(name.toLowerCase(), index);
	}

	private int getTabNameIndex(String tabName) {
		if (!tabNames.containsKey(tabName.toLowerCase())) {
			throw new StockFxException(String.format("NO tab with name %s exists", tabName));
		}
		return tabNames.get(tabName.toLowerCase());
	}

	private void manageNewTab(String tabName, Tab newTab) {
		int indexOfTab = getTabPane().getTabs().indexOf(newTab);
		System.out.println("indexOfTab:" + indexOfTab);
		addTabNameMapEntry(tabName, indexOfTab);
	}

	@Override
	public void addTabWithNode(String tabName, Node root) {
		addTabWithNode(tabName, root, TabManagerParameters.startParams()); // use default parameters
	}

	@Override
	public void addTabWithNode(String tabName, Node root, TabManagerParameters params) {
		if (params.isOpenNotAdd()) {
			System.out.println("-> addTabWithNode() - isOpenNotAdd");
			if (tabNames.containsKey(tabName.toLowerCase())) {
				System.out.println("-> addTabWithNode() - isOpenNotAdd add tabs already has a "+tabName);
				getTabPane().getSelectionModel().select(tabNames.get(tabName.toLowerCase()));
				return;
			}
		}
		else {
			System.out.println("-> addTabWithNode() - isOpenNotAdd  NOT true - params are:"+params);
		}
		int indexToInsertAt = tabsLength(); // the end by default
		if (params.getInsertAfter().length() > 0) {
			indexToInsertAt = getTabNameIndex(params.getInsertAfter()) + 1;
		} else if (params.getInsertBefore().length() > 0) {
			indexToInsertAt = getTabNameIndex(params.getInsertBefore());
		} else if (params.getInsertAt() != null) {
			// if -1 then leave at 'the end by default'
			if (params.getInsertAt() != -1) {
				indexToInsertAt = params.getInsertAt();
			}
		}
		if (indexToInsertAt < 0) {
			indexToInsertAt = 0;	// force to insert at first place
		} else if (indexToInsertAt > tabsLength()) {
			indexToInsertAt = tabsLength();
		}
		if (tabWithNameExists(tabName)) {
			if (params.isUseSuffix()) {
				tabName = addSuffix(tabName);
			} else {
				throw new StockFxException(String.format(
						"Tab with name '%s' already exists.  Use TabManagerParam.useSuffix(true) as an option.",
						tabName));
			}
		}
		Tab newTab = new Tab(tabName);
		newTab.setContent(root);
		getTabPane().getTabs().add(indexToInsertAt, newTab);
		getTabPane().getSelectionModel().select(newTab);
		manageNewTab(tabName, newTab);
	}

	private int tabsLength() {
		return getTabPane().getTabs().size();
	}

	private boolean tabWithNameExists(String tabName) {
		return tabNames.containsKey(tabName.toLowerCase());
	}

	private String addSuffix(String tabName) {
		Integer i = 1;
		String suffixVer = tabName;
		String lastSuffixVer = null;
		do {
			lastSuffixVer = suffixVer;
			suffixVer = tabName + " " + i.toString();
			i++;
		} while (tabWithNameExists(suffixVer));
		return suffixVer;
	}

	/**
	 * Add a new document to a new tab and open it after the tab with the given tabName
	 * 
	 * @param tabName of document of tab to add
	 * @param scene graph root
	 * @param tabNameAFter of the tab to add this after
	 */
//	public void addNewDocument(String tabName, Node root, String tabNameAFter) {
//		int indexOfExistingTab = getTabNameIndex(tabNameAFter);
//		Tab newTab = new Tab(tabName);
//		newTab.setContent(root);
//		getTabPane().getTabs().add(indexOfExistingTab + 1, newTab);
//		getTabPane().getSelectionModel().select(newTab);
//		manageNewTab(tabName, newTab);
//	}

	public void selectTab(String tabName) {
		if (tabNames.containsKey(tabName.toLowerCase())) {
			getTabPane().getSelectionModel().select(tabNames.get(tabName.toLowerCase()));
		}
	}

}
