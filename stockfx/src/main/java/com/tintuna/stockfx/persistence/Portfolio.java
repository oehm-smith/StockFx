package com.tintuna.stockfx.persistence;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.tintuna.stockfx.application.MainApplication;

@Entity
@NamedQueries({ @NamedQuery(name = "Portfolio.findAll", query = "SELECT p FROM Portfolio p"),
		@NamedQuery(name = "Portfolio.findById", query = "SELECT p FROM Portfolio p WHERE p.id = :id"),
		@NamedQuery(name = "Portfolio.findByName", query = "SELECT p FROM Portfolio p WHERE p.name = :name"),
		@NamedQuery(name = "Portfolio.findByType", query = "SELECT p FROM Portfolio p WHERE p.name = :type") })
@XmlRootElement
public class Portfolio {
	private long id;

	private StringProperty name;
	private StringProperty type;
	private ObservableList<Stock> stocksInThisPortfolio;

	public Portfolio() {

	}

	public Portfolio(String name, String type) {
		setName(name);
		setType(type);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@ManyToMany//(mappedBy = "portfoliosThatContainThisStock") 	// , fetch=FetchType.EAGER)
	public List<Stock> getStocksInThisPortfolio() {
		ObservableList<Stock> setOStocks = getobservableStocksInThisPortfolio();
		List<Stock> s = new ArrayList<Stock>(setOStocks);
		System.out.println("Portfolio -> getStocksInThisPortfolio: "+s);
		return s;
	}

	@Transient
	public ObservableList<Stock> getobservableStocksInThisPortfolio() {
		if (stocksInThisPortfolio == null) {
			stocksInThisPortfolio = FXCollections.observableArrayList();
		}
		return stocksInThisPortfolio;
	}

	public void setStocksInThisPortfolio(List<Stock> stocksInThisportfolio) {
		this.stocksInThisPortfolio = FXCollections.observableList(stocksInThisportfolio);
		// There is a complete HANG in here if the fetch isn't set to eager or this isEmpty() test isn't performed
		stocksInThisportfolio.isEmpty();
	}

	public void addStock(Stock s) {
//		System.out.println("addStock(" + s + ")");
		getobservableStocksInThisPortfolio().add(s);
	}

	public String toString() {
		stocksInThisPortfolio.isEmpty(); // Force the Set to Eagerly load
		return getId() + " = " + getName() + " : " + getType() + " - Stocks=" + stocksInThisPortfolio;
	}

}