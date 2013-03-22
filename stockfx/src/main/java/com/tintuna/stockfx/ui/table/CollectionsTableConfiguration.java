package com.tintuna.stockfx.ui.table;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import com.tintuna.stockfx.persistence.PCollection;


public class CollectionsTableConfiguration extends AbstractTableSettings {

	public static void setupTable(TableView<PCollection> stockTable) {
		addColumns(stockTable, getTableColumnsDefinition());
	}
	
	private static Map<String, Callback<CellDataFeatures<PCollection, String>, ObservableValue<String>>> getTableColumnsDefinition() {
		LinkedHashMap<String, Callback<CellDataFeatures<PCollection, String>, ObservableValue<String>>> columnDefinitions = new LinkedHashMap<>();
		Callback<CellDataFeatures<PCollection, String>, ObservableValue<String>> cellCallbackName = new Callback<CellDataFeatures<PCollection, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(final CellDataFeatures<PCollection, String> entity) {
				return new ObservableValueBase<String>() {
					@Override
					public String getValue() {
						return entity.getValue().getName();
					}
				};
			}
		};

		columnDefinitions.put("Name", cellCallbackName);
		return columnDefinitions;
	}

}
