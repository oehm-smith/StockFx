package com.tintuna.stockfx.persistence;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Entity
@NamedQueries({ @NamedQuery(name = "Stock.findAll", query = "SELECT p FROM Stock p"), @NamedQuery(name = "Stock.findById", query = "SELECT s FROM Stock s WHERE s.id = :id"),
		@NamedQuery(name = "Stock.findBySymbol", query = "SELECT s FROM Stock s WHERE s.symbol = :symbol"),
		@NamedQuery(name = "Stock.findByCompanyName", query = "SELECT s FROM Stock s WHERE s.companyName = :companyName") })
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "symbol", "companyName" }))
public class Stock_old implements Comparable {
	private static final Logger log = LoggerFactory.getLogger(Stock_old.class);

	private long id;

	private StringProperty symbol;
	private StringProperty companyName;
	private ObservableList<Portfolio> portfoliosThatContainThisStock;

	public Stock_old() {

	}

	public Stock_old(String symbol, String company) {
		setSymbol(symbol);
		setCompanyName(company);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getSymbol() {
		if (symbol == null) {
			return null;
		}
		return symbol.get();
	}

	public void setSymbol(String name) {
		getSymbolProperty().set(name);
	}

	public StringProperty getSymbolProperty() {
		if (symbol == null) {
			symbol = new SimpleStringProperty();
		}
		return symbol;
	}

	@Column(nullable = false)
	public String getCompanyName() {
		if (companyName == null) {
			return "";
		}
		return companyName.get();
	}

	public void setCompanyName(String type) {
		getCompanyNameProperty().set(type);
	}

	public StringProperty getCompanyNameProperty() {
		if (companyName == null) {
			companyName = new SimpleStringProperty();
		}
		return companyName;
	}

	// (fetch=FetchType.EAGER)
	@ManyToMany(mappedBy = "StocksInThisPortfolio")
	public List<Portfolio> getPortfoliosThatContainThisStock() {
		List<Portfolio> s = new ArrayList<Portfolio>(getobservablePortfoliosThatContainThisStock());
		return s;
	}

	@Transient
	public ObservableList<Portfolio> getobservablePortfoliosThatContainThisStock() {
		if (portfoliosThatContainThisStock == null) {
			portfoliosThatContainThisStock = FXCollections.observableArrayList();
		}
		return portfoliosThatContainThisStock;
	}

	public void setPortfoliosThatContainThisStock(List<Portfolio> portfoliosThatContainThisStock) {
		this.portfoliosThatContainThisStock = FXCollections.observableList(portfoliosThatContainThisStock);
		// There is a complete HANG in here if the fetch isn't set to eager or this isEmpty() test isn't performed
		portfoliosThatContainThisStock.isEmpty();
	}

	public void addPortfolio(Portfolio p) {
		getobservablePortfoliosThatContainThisStock().add(p);
	}

	/* ******************** */
	public boolean equals(Object obj) {
		if (obj instanceof Stock_old) {
			Stock_old s = (Stock_old) obj;
			return (s.getSymbol().equals(this.getSymbol())) && s.getCompanyName().equals(this.getCompanyName());
		} else {
			return false;
		}
	}

	public int hashCode() {
		return (getSymbol() + getCompanyName()).hashCode();
	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof Stock_old) {
			Stock_old s = (Stock_old) obj;
			return ((s.getSymbol() + s.getCompanyName()).compareTo((this.getSymbol() + this.getCompanyName())));
		} else {
			return -1;
		}
	}

	@Override
	public String toString() {
		portfoliosThatContainThisStock.isEmpty(); // Force the Set to Eagerly load
		return getId() + " = " + getSymbol() + " : " + getCompanyName();
	}

	public String toStringWithPortfolio() {
		portfoliosThatContainThisStock.isEmpty(); // Force the Set to Eagerly load
		return getId() + " = " + getSymbol() + " : " + getCompanyName() + " - Portfolios=" + portfoliosThatContainThisStock.size();
	}

}
