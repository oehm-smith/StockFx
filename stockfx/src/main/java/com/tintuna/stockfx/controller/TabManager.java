package com.tintuna.stockfx.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.exception.StockFxException;
import com.tintuna.stockfx.util.TabManagerParameters;

public class TabManager implements TabI {
	private static final Logger log = LoggerFactory.getLogger(MainApplication.class);
	private TabPane tabPane;

	public TabManager(TabPane tabpane) {
		this.tabPane = tabpane;
	}

	// must instantiate with a tabPane
	private TabManager() {
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

	@Override
	public void addTabWithNode(String tabName, Node root) {
		addTabWithNode(tabName, root, TabManagerParameters.startParams()); // use default parameters
	}

	@Override
	public void addTabWithNode(String tabName, Node root, TabManagerParameters params) {
		int tabIndex;
		int indexToInsertAt;
		if (params.isOpenNotAdd()) {
			log.debug("-> addTabWithNode() - isOpenNotAdd");
			if ((tabIndex = getIndexOfTabWithName(tabName)) >= 0) {
				log.debug("-> addTabWithNode() - isOpenNotAdd add tabs already has a " + tabName);
				// Insert new content
				getTabPane().getTabs().get(tabIndex).setContent(root);
				getTabPane().getSelectionModel().select(tabIndex);
				return;
			}
		} else {
			log.debug("-> addTabWithNode() - isOpenNotAdd  NOT true - params are:" + params);
		}
		indexToInsertAt = numberOfTabs(); // the end by default
		if (params.getInsertAfter().length() > 0) {
			indexToInsertAt = getIndexOfTabWithName(params.getInsertAfter()) + 1;
		} else if (params.getInsertBefore().length() > 0) {
			indexToInsertAt = getIndexOfTabWithName(params.getInsertBefore());
		} else if (params.getInsertAt() != null) {
			// if -1 then leave at 'the end by default'
			if (params.getInsertAt() != -1) {
				indexToInsertAt = params.getInsertAt();
			}
		}
		if (indexToInsertAt < 0) {
			indexToInsertAt = 0; // force to insert at first place
		} else if (indexToInsertAt > numberOfTabs()) {
			indexToInsertAt = numberOfTabs();
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
	}

	/**
	 * @param tabName
	 * @return index of tab with given name, or -1 if doesn't exist
	 */
	protected int getIndexOfTabWithName(String tabName) {
		// Trade off - could use a data structure, or use this slightly more (time) complex. The assumption is that
		// there won't be many tabs. Certainly less than 100 will be no problems.
		for (Tab t : getTabPane().getTabs()) {
			if (t.getText().equalsIgnoreCase(tabName)) {
				return getTabPane().getTabs().indexOf(t);
			}
		}
		return -1;
	}

	private int numberOfTabs() {
		return getTabPane().getTabs().size();
	}

	private boolean tabWithNameExists(String tabName) {
		return getIndexOfTabWithName(tabName) >= 0;
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

	public void selectTab(String tabName) {
		int index;
		if ((index = getIndexOfTabWithName(tabName)) >= 0) {
			getTabPane().getSelectionModel().select(index);
		}
	}

}
