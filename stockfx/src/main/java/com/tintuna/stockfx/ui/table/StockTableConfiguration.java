package com.tintuna.stockfx.ui.table;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import com.tintuna.stockfx.persistence.PortfolioStock;

public class StockTableConfiguration extends AbstractTableSettings {

	public static void setupTable(TableView<PortfolioStock> stockTable) {
		addColumns(stockTable, getTableColumnsDefinition());
	}
	
	private static Map<String, Callback<CellDataFeatures<PortfolioStock, String>, ObservableValue<String>>> getTableColumnsDefinition() {
		LinkedHashMap<String, Callback<CellDataFeatures<PortfolioStock, String>, ObservableValue<String>>> columnDefinitions = new LinkedHashMap<>();
		Callback<CellDataFeatures<PortfolioStock, String>, ObservableValue<String>> cellCallbackSymbol = new Callback<CellDataFeatures<PortfolioStock, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(final CellDataFeatures<PortfolioStock, String> portfolioStock) {
				return new ObservableValueBase<String>() {
					@Override
					public String getValue() {
						return portfolioStock.getValue().getStockid().getSymbol();
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
						return portfolioStock.getValue().getStockid().getCompanyName();
					}
				};
			}
		};

		columnDefinitions.put("symbol", cellCallbackSymbol);
		columnDefinitions.put("companyName", cellCallbackCompanyName);
		return columnDefinitions;
	}

}
