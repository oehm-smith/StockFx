/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tintuna.stockfx.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.MainApplication;

/**
 * 
 * @author Brooke Smith brooke@tintuna.org
 */
@Entity
@Table(name = "portfolio")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Portfolio.findAll", query = "SELECT p FROM Portfolio p"), @NamedQuery(name = "Portfolio.findById", query = "SELECT p FROM Portfolio p WHERE p.id = :id"),
		@NamedQuery(name = "Portfolio.findByName", query = "SELECT p FROM Portfolio p WHERE p.name = :name") })
public class Portfolio implements Serializable {
	private static final Logger log = LoggerFactory.getLogger(MainApplication.class);
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@Column(name = "name")
	private String name;
	// @JoinColumn(name = "PortfolioType_id", referencedColumnName = "id")
	// @ManyToOne
	@Enumerated(EnumType.STRING)
	private PType portfolioType;
	@Column(name = "HRN_SRN")
	private String hrnSrn;
	@JoinColumn(name = "Collection_id", referencedColumnName = "id")
	@ManyToOne
	private PCollection collection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolio")
	private Collection<PortfolioStock> portfoliostockCollection;

	public Portfolio() {
	}

	public Portfolio(Integer id) {
		this.id = id;
	}

	public Portfolio(String name) {
		this.name = name;
	}

	public Portfolio(String name, PType type) {
		this.name = name;
		this.portfolioType = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PType getPortfolioType() {
		return portfolioType;
	}

	public void setPortfolioType(PType portfolioType) {
		this.portfolioType = portfolioType;
	}

	public String getHrnSrn() {
		return hrnSrn;
	}

	public void setHrnSrn(String hrnSrn) {
		this.hrnSrn = hrnSrn;
	}

	public PCollection getCollection() {
		return collection;
	}

	public void setCollection(PCollection collectionid) {
		this.collection = collectionid;
	}

	@XmlTransient
	public Collection<PortfolioStock> getPortfoliostockCollection() {
		return portfoliostockCollection;
	}

	public void setPortfoliostockCollection(Collection<PortfolioStock> portfoliostockCollection) {
		this.portfoliostockCollection = portfoliostockCollection;
		log.debug("setPortfoliostockCollection - " + portfoliostockCollection);
	}

	public void addPortfolioStock(PortfolioStock stock) {
		this.portfoliostockCollection.add(stock);
		log.debug("addPortfolioStock - " + stock);
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
		if (!(object instanceof Portfolio)) {
			return false;
		}
		Portfolio other = (Portfolio) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Portfolio[ id=" + id + ", name=" + name + ", #stocks:" + getPortfoliostockCollection().size() + " ]";
	}

}
