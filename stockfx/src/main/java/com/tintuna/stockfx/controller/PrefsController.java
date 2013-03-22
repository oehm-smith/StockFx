package com.tintuna.stockfx.controller;

import javafx.beans.value.ChangeListener;

public interface PrefsController {
	void addDBPathLostFocusListener(ChangeListener<Boolean> listener);
}
