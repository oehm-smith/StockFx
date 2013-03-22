package com.tintuna.stockfx.ui.table;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.persistence.PortfolioStock;

public class PortfolioTableConfiguration extends AbstractTableSettings {

	public static void setupTable(TableView<Portfolio> stockTable) {
		addColumns(stockTable, getTableColumnsDefinition());
	}
	
	private static Map<String, Callback<CellDataFeatures<Portfolio, String>, ObservableValue<String>>> getTableColumnsDefinition() {
		LinkedHashMap<String, Callback<CellDataFeatures<Portfolio, String>, ObservableValue<String>>> columnDefinitions = new LinkedHashMap<>();
		Callback<CellDataFeatures<Portfolio, String>, ObservableValue<String>> cellCallbackSymbol = new Callback<CellDataFeatures<Portfolio, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(final CellDataFeatures<Portfolio, String> entity) {
				return new ObservableValueBase<String>() {
					@Override
					public String getValue() {
						return entity.getValue().getName();
					}
				};
			}
		};

		Callback<CellDataFeatures<Portfolio, String>, ObservableValue<String>> cellCallbackCompanyName = new Callback<CellDataFeatures<Portfolio, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(final CellDataFeatures<Portfolio, String> entity) {
				return new ObservableValueBase<String>() {
					@Override
					public String getValue() {
						return entity.getValue().getHrnSrn();
					}
				};
			}
		};

		columnDefinitions.put("Name", cellCallbackSymbol);
		columnDefinitions.put("HrnSrn", cellCallbackCompanyName);
		return columnDefinitions;
	}

}
