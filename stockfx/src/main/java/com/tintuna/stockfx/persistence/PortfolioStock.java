/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tintuna.stockfx.persistence;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brooke Smith brooke@tintuna.org
 */
@Entity
@Table(name = "portfoliostock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PortfolioStock.findAll", query = "SELECT p FROM PortfolioStock p"),
    @NamedQuery(name = "PortfolioStock.findById", query = "SELECT p FROM PortfolioStock p WHERE p.id = :id"),
    @NamedQuery(name = "PortfolioStock.findByDrp", query = "SELECT p FROM PortfolioStock p WHERE p.drp = :drp"),
    @NamedQuery(name = "PortfolioStock.findByHrnSrn", query = "SELECT p FROM PortfolioStock p WHERE p.hrnSrn = :hrnSrn")})
public class PortfolioStock implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "DRP")
    private Integer drp;
    @Column(name = "HRN_SRN")
    private String hrnSrn;
    @JoinColumn(name = "Stock_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Stock stockid;
    @JoinColumn(name = "StockHolding_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stockholding stockHoldingid;
    @JoinColumn(name = "Portfolio_id", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade=CascadeType.ALL)
    private Portfolio portfolioid;

    public PortfolioStock() {
    }

    public PortfolioStock(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDrp() {
        return drp;
    }

    public void setDrp(Integer drp) {
        this.drp = drp;
    }

    public String getHrnSrn() {
        return hrnSrn;
    }

    public void setHrnSrn(String hrnSrn) {
        this.hrnSrn = hrnSrn;
    }

    public Stock getStockid() {
        return stockid;
    }

    public void setStockid(Stock stockid) {
        this.stockid = stockid;
    }

    public Stockholding getStockHoldingid() {
        return stockHoldingid;
    }

    public void setStockHoldingid(Stockholding stockHoldingid) {
        this.stockHoldingid = stockHoldingid;
    }

    public Portfolio getPortfolioid() {
        return portfolioid;
    }

    public void setPortfolioid(Portfolio portfolioid) {
        this.portfolioid = portfolioid;
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
        if (!(object instanceof PortfolioStock)) {
            return false;
        }
        PortfolioStock other = (PortfolioStock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Portfoliostock[ id=%d, drp=%d, hin=%s, stock - %s, portfolios: %s", id, drp, hrnSrn, portfolioid, stockid);
    }

    @Override
	public int compareTo(Object obj) {
		if (obj instanceof PortfolioStock) {
			PortfolioStock s = (PortfolioStock) obj;
			return ((s.getStockid().getSymbol() + s.getStockid().getCompanyName()).compareTo((this.getStockid().getSymbol() + this.getStockid().getCompanyName())));
		} else {
			return -1;
		}
	}
    
}
