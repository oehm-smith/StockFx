package com.tintuna.stockfx.util;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

public class StringUtils {
	public static boolean isNotNullEmpty(TextField strField) {
		return (!(strField == null || strField.getText().isEmpty()));
	}
	
	public static boolean isNotNullEmpty(String string) {
		return (!(string == null || string.isEmpty()));
	}
	
	public static boolean isNotNullEmpty(Tab tab) {
		return (!(tab == null || tab.getText().isEmpty()));
	}
}
