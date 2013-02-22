package com.tintuna.stockfx.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Portfolio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private StringProperty name;
	private StringProperty type;

	public Portfolio(String name, String type) {
		setName(name);
		setType(type);
	}

	public String getName() {
		if (name == null) {
			return "";
		}
		return name.get();
	}

	public void setName(String name) {
		getNameProperty().set(name);
	}

	public StringProperty getNameProperty() {
		if (name == null) {
			name = new SimpleStringProperty();
		}
		return name;
	}

	public String getType() {
		if (type == null) {
			return "";
		}
		return type.get();
	}

	public void setType(String type) {
		getTypeProperty().set(type);
	}

	public StringProperty getTypeProperty() {
		if (type == null) {
			type = new SimpleStringProperty();
		}
		return type;
	}
	
	public String toString() {
		return getName()+" : "+getType();
	}
}
