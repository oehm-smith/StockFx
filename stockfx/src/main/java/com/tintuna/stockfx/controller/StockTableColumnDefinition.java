package com.tintuna.stockfx.controller;

import java.util.LinkedHashMap;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import com.tintuna.stockfx.persistence.PortfolioStock;

// TODO - no longer needed
public class StockTableColumnDefinition extends LinkedHashMap<String, Callback<CellDataFeatures<PortfolioStock, String>, ObservableValue<String>>> {
	private static final long serialVersionUID = 1L;

	public StockTableColumnDefinition() {
		Callback<CellDataFeatures<PortfolioStock, String>, ObservableValue<String>> cellCallbackSymbol = new Callback<CellDataFeatures<PortfolioStock, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(final CellDataFeatures<PortfolioStock, String> portfolioStock) {
				return new ObservableValueBase<String>() {
					@Override
					public String getValue() {
						return portfolioStock.getValue().getStock().getSymbol();
					}
				};
			}
		};

		Callback<CellDataFeatures<PortfolioStock, String>, ObservableValue<String>> cellCallbackCompanyName = new Callback<CellDataFeatures<PortfolioStock, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(final CellDataFeatures<PortfolioStock, String> portfolioStock) {
				return new ObservableValueBase<String>() {
					@Override
					public String getValue() {
						return portfolioStock.getValue().getStock().getCompanyName();
					}
				};
			}
		};

		put("symbol", cellCallbackSymbol);
		put("companyName", cellCallbackCompanyName);
	}
}