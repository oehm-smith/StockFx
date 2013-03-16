/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tintuna.stockfx.persistence;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Brooke Smith brooke@tintuna.org
 */
@Entity
@Table(name = "collection")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "PCollection.findAll", query = "SELECT p FROM PCollection p"), @NamedQuery(name = "PCollection.findById", query = "SELECT p FROM PCollection p WHERE p.id = :id"),
		@NamedQuery(name = "PCollection.findByName", query = "SELECT p FROM PCollection p WHERE p.name = :name") })
public class PCollection implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@Column(name = "name")
	private String name;
	@OneToMany(mappedBy = "collectionid")
    private Collection<Portfolio> portfolioCollection;

	public PCollection() {
	}

	public PCollection(Integer id) {
		this.id = id;
	}

	public PCollection(String name) {
		this.name = name;
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

	@XmlTransient
	public Collection<Portfolio> getPortfolioCollection() {
		return portfolioCollection;
	}

	public void setPortfolioCollection(Collection<Portfolio> portfolioCollection) {
		this.portfolioCollection = portfolioCollection;
	}

	public void addPortfolio(Portfolio p) {
		this.portfolioCollection.add(p);
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
		if (!(object instanceof PCollection)) {
			return false;
		}
		PCollection other = (PCollection) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.tintuna.stockfx.persistence.PCollection[ id=" + id + " ]";
	}

}
