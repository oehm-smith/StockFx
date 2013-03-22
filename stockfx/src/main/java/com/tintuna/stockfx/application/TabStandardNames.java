package com.tintuna.stockfx.application;

public enum TabStandardNames {
	Collection("Collections"), Portfolios("Portfolios"), Portfolio("New Portfolio"), Stock("New Stock"), Prefs("Preferences");

	private String text;

	TabStandardNames(String text) {
		this.text = text;
	}
}
