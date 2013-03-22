/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tintuna.stockfx.persistence;

import java.io.Serializable;
import java.util.Collection;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Brooke Smith brooke@tintuna.org
 */
@Entity
@Table(name = "stock")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s"), @NamedQuery(name = "Stock.findById", query = "SELECT s FROM Stock s WHERE s.id = :id"),
		@NamedQuery(name = "Stock.findBySymbol", query = "SELECT s FROM Stock s WHERE s.symbol = :symbol"),
		@NamedQuery(name = "Stock.findByCompanyName", query = "SELECT s FROM Stock s WHERE s.companyName = :companyName"),
		@NamedQuery(name = "Stock.findByUrl", query = "SELECT s FROM Stock s WHERE s.url = :url") })
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String symbol;
	private StringProperty companyName;
	private String url;
	private Collection<Other> otherCollection;
	private Collection<Morningstar> morningstarCollection;
	private Collection<Pricehistory> pricehistoryCollection;
	private Collection<Dividends> dividendsCollection;
	private Exchange exchangeid;
	private Collection<Motleyfool> motleyfoolCollection;
	private PortfolioStock portfoliostock;

	public Stock() {
	}

	public Stock(Integer id) {
		this.id = id;
	}

	public Stock(Integer id, String symbol, String companyName, String url) {
		this.id = id;
		this.symbol = symbol;
		setCompanyName(companyName);
		this.url = url;
	}

	public Stock(String symbol, String companyName, String url) {
		this.symbol = symbol;
		setCompanyName(companyName);
		this.url = url;
	}

	public Stock(String symbol, String companyName) {
		this.symbol = symbol;
		setCompanyName(companyName);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic(optional = false)
	@Column(name = "symbol")
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Basic(optional = false)
	@Column(name = "companyName")
	public String getCompanyName() {
		return getCompanyNameProperty().get();
	}

	public void setCompanyName(String companyName) {
		getCompanyNameProperty().set(companyName);
	}

	public StringProperty getCompanyNameProperty() {
		if (companyName == null) {
			companyName = new SimpleStringProperty();
		}
		return companyName;
	}

	@Basic(optional = false)
	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@XmlTransient
	@OneToMany(mappedBy = "stockid")
	public Collection<Other> getOtherCollection() {
		return otherCollection;
	}

	public void setOtherCollection(Collection<Other> otherCollection) {
		this.otherCollection = otherCollection;
	}

	@XmlTransient
	@OneToMany(mappedBy = "stockid")
	public Collection<Morningstar> getMorningstarCollection() {
		return morningstarCollection;
	}

	public void setMorningstarCollection(Collection<Morningstar> morningstarCollection) {
		this.morningstarCollection = morningstarCollection;
	}

	@XmlTransient
	@OneToMany(mappedBy = "stockid")
	public Collection<Pricehistory> getPricehistoryCollection() {
		return pricehistoryCollection;
	}

	public void setPricehistoryCollection(Collection<Pricehistory> pricehistoryCollection) {
		this.pricehistoryCollection = pricehistoryCollection;
	}

	@XmlTransient
	@OneToMany(mappedBy = "stockid")
	public Collection<Dividends> getDividendsCollection() {
		return dividendsCollection;
	}

	public void setDividendsCollection(Collection<Dividends> dividendsCollection) {
		this.dividendsCollection = dividendsCollection;
	}

	@JoinColumn(name = "Exchange_id", referencedColumnName = "id")
	@OneToOne
	public Exchange getExchangeid() {
		return exchangeid;
	}

	public void setExchangeid(Exchange exchangeid) {
		this.exchangeid = exchangeid;
	}

	@XmlTransient
	@OneToMany(mappedBy = "stockid")
	public Collection<Motleyfool> getMotleyfoolCollection() {
		return motleyfoolCollection;
	}

	public void setMotleyfoolCollection(Collection<Motleyfool> motleyfoolCollection) {
		this.motleyfoolCollection = motleyfoolCollection;
	}

	@OneToOne(mappedBy = "stockid")
	public PortfolioStock getPortfoliostock() {
		return portfoliostock;
	}

	public void setPortfoliostock(PortfolioStock portfoliostock) {
		this.portfoliostock = portfoliostock;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Stock)) {
			return false;
		}
		Stock other = (Stock) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Stock[ id=" + id + ", Symbol=" + symbol + ", company:" + companyName + " ]";
	}

}
