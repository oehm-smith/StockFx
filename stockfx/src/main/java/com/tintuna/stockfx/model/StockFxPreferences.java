package com.tintuna.stockfx.model;

import java.util.prefs.Preferences;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.util.StringUtils;

public class StockFxPreferences {
	private static StockFxPreferences stockFxPreferences = null;
	private static final Logger log = LoggerFactory.getLogger(MainApplication.class);
	Preferences preferences;
	StringProperty databaseURL;
	StringProperty morningStarUserName;
	StringProperty morningStarPW;

	// Singleton
	public static StockFxPreferences instance() {
		if (stockFxPreferences == null) {
			stockFxPreferences = new StockFxPreferences();
		}
		return stockFxPreferences;
	}

	private StockFxPreferences() {
		initHandlers();
		preferences = Preferences.userNodeForPackage(getClass());
		setDatabaseURL(preferences.get("databaseURL", "/tmp/a/sub/dir/StockFx"));
		setMorningStarUserName(preferences.get("morningStarUserName", "me"));
		setMorningStarPW(preferences.get("morningStarPW", "xxx"));
	}

	public String getDatabaseURL() {
		return databaseURL.get();
	}

	public void setDatabaseURL(String databaseURL) {
		getDatabaseURLProperty().set(databaseURL);
	}

	public StringProperty getDatabaseURLProperty() {
		if (databaseURL == null) {
			databaseURL = new SimpleStringProperty();
		}
		return databaseURL;
	}

	public String getMorningStarUserName() {
		return morningStarUserName.get();
	}

	public void setMorningStarUserName(String morningStarUserName) {
		getMorningStarUserNameProperty().set(morningStarUserName);
	}

	public StringProperty getMorningStarUserNameProperty() {
		if (morningStarUserName == null) {
			morningStarUserName = new SimpleStringProperty();
		}
		return morningStarUserName;
	}

	public String getMorningStarPW() {
		return morningStarPW.get();
	}

	public void setMorningStarPW(String morningStarPW) {
		getMorningStarPWProperty().set(morningStarPW);
	}

	public StringProperty getMorningStarPWProperty() {
		if (morningStarPW == null) {
			morningStarPW = new SimpleStringProperty();
		}
		return morningStarPW;
	}

	private void initHandlers() {
		getDatabaseURLProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (checkedChanged(arg1, arg2)) {
					log.debug("db pref changed");
					preferences.put("databaseURL", getDatabaseURL());
				}
			}

		});
		getMorningStarUserNameProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (checkedChanged(arg1, arg2)) {
					preferences.put("morningStarUserName", getMorningStarUserName());
				}
			}

		});
		getMorningStarPWProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (checkedChanged(arg1, arg2)) {
					preferences.put("morningStarPW", getMorningStarPW());
				}
			}

		});
	}

	protected boolean checkedChanged(String arg1, String arg2) {
		boolean changed = false;
		if (StringUtils.isNotNullEmpty(arg1)) {
			if (StringUtils.isNotNullEmpty(arg2)) {
				if (!arg1.equals(arg2)) {
					changed = true;
				}
			}
			// else wiping out the value is not a change - ie. I don't want to persist such a change
		} else {
			changed = true;
		}
		return changed;
	}
}
