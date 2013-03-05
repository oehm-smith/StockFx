package com.tintuna.stockfx.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.exception.StockFxException;
import com.tintuna.stockfx.util.StringUtils;
import com.tintuna.stockfx.util.TabManagerParameters;

public class TabManager implements TabI {
	private static final Logger log = LoggerFactory.getLogger(TabManager.class);
	private TabPane tabPane;

	public TabManager(TabPane tabpane) {
		setTabPane(tabpane);

	}

	public TabManager() {
	}

	public void setTabPane(TabPane tabPane) {
		this.tabPane = tabPane;
		initTabPane();
	}

	private void initTabPane() {
		// There is a Tab.onSelectionChanged() property but you can't see if it was selected or deselected
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> tab, Tab oldTab, Tab newTab) {
				if (oldTab != null) {
					deselectedTab(oldTab);
				}
				if (oldTab != null) {
					selectedTab(newTab);
				}
			}
		});
	}

	protected TabPane getTabPane() {
		if (tabPane == null) {
			throw new RuntimeException("TabPane needs to be set with setTabPane(TabPane)");
		}
		return tabPane;
	}

	@Override
	public void addTabWithNode(String tabName, Controller controller) {
		addTabWithNode(tabName, controller, TabManagerParameters.startParams()); // use default parameters
	}

	@Override
	public void addTabWithNode(String tabName, Controller controller, TabManagerParameters params) {
		int tabIndex;
		int indexToInsertAt;
		if (params.isOpenNotAdd()) {
			log.debug("-> addTabWithNode() - isOpenNotAdd");
			if ((tabIndex = getIndexOfTabWithName(tabName)) >= 0) {
				log.debug("-> addTabWithNode() - isOpenNotAdd add tabs already has a " + tabName);
				// Insert new content
				getTabPane().getTabs().get(tabIndex).setContent(controller.getRoot());
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
		log.debug("New Tab: "+tabName+", inseted at:"+indexToInsertAt);
		newTab.setContent(controller.getRoot());
		newTab.getProperties().put(Controller.class, controller); // When the user selects that tab the contents need to
																	// be updated with a new Controller in some cases
		getTabPane().getTabs().add(indexToInsertAt, newTab);
		getTabPane().getSelectionModel().select(newTab);
	}

	protected void selectedTab(Tab newTab) {
		Controller existingController = (Controller) newTab.getProperties().get(Controller.class);
		if (existingController instanceof StockController) {
			// tab selected is for Stocks, need to create a new controller (for example a different portfolio was selected and now need a new controller to mirror this)
			StockController sc = MainApplication.getAppFactory().getStockController();
			newTab.getProperties().put(Controller.class, sc);
			newTab.setContent(sc.getRoot());
		}
	}

	protected void deselectedTab(Tab oldTab) {

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
