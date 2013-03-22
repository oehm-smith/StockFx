package com.tintuna.stockfx.ui.table;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class AbstractTableSettings {
	protected static <T> void addColumns(TableView<T> stockTable, Map<String, Callback<CellDataFeatures<T, String>, ObservableValue<String>>> tableColumnsDefinition) {
		stockTable.getColumns().clear();
		stockTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		Set<Entry<String, Callback<CellDataFeatures<T, String>, ObservableValue<String>>>> columnDefinitionsSet = tableColumnsDefinition.entrySet();
		Iterator<Entry<String, Callback<CellDataFeatures<T, String>, ObservableValue<String>>>> iter = columnDefinitionsSet.iterator();
		while (iter.hasNext()) {
			Entry<String, Callback<CellDataFeatures<T, String>, ObservableValue<String>>> colDefEntry = iter.next();
			TableColumn<T, String> col = new TableColumn<>(colDefEntry.getKey());
			col.setText(colDefEntry.getKey());
			col.setCellValueFactory(colDefEntry.getValue());
			stockTable.getColumns().add(col);
		}		
	}
}
