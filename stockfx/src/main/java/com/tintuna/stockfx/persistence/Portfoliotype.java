/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tintuna.stockfx.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.management.relation.Role;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.annotations.ConversionValue;
import org.eclipse.persistence.annotations.ObjectTypeConverter;

/**
 *
 * @author Brooke Smith brooke@tintuna.org
 */
//@Entity
@Table(name = "portfoliotype")
// Pre-populate table with enum values
@ObjectTypeConverter (
	    name = "ptypeEnumFromStringConversion",
	    objectType = PType.class,
	    dataType = String.class,
	    conversionValues = {
	        @ConversionValue(objectValue = "HOLDING", dataValue = "HOLDING"),
	        @ConversionValue(objectValue = "WATCHLIST", dataValue = "WATCHLIST"),
	        @ConversionValue(objectValue = "THIRDPARTY", dataValue = "THIRDPARTY")
	    }
	)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PortfolioType.findAll", query = "SELECT p FROM PortfolioType p"),
    @NamedQuery(name = "PortfolioType.findById", query = "SELECT p FROM PortfolioType p WHERE p.id = :id"),
    @NamedQuery(name = "PortfolioType.findByType", query = "SELECT p FROM PortfolioType p WHERE p.type = :type")})
// TODO - not being used anymore?
public class PortfolioType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PType type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolioTypeid")
    private Collection<Portfolio> portfolioCollection;

    public PortfolioType() {
    }

    public PortfolioType(Integer id) {
        this.id = id;
    }

    public PortfolioType(PType type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PType getType() {
        return type;
    }

    public void setType(PType type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<Portfolio> getPortfolioCollection() {
        return portfolioCollection;
    }

    public void setPortfolioCollection(Collection<Portfolio> portfolioCollection) {
        this.portfolioCollection = portfolioCollection;
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
        if (!(object instanceof PortfolioType)) {
            return false;
        }
        PortfolioType other = (PortfolioType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tintuna.stockfx.persistence.Portfoliotype[ id=" + id + " ]";
    }
    
}
