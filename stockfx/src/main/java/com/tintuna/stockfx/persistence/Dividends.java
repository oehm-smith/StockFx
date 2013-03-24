/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tintuna.stockfx.persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brooke Smith brooke@tintuna.org
 */
@Entity
@Table(name = "dividends")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dividends.findAll", query = "SELECT d FROM Dividends d"),
    @NamedQuery(name = "Dividends.findById", query = "SELECT d FROM Dividends d WHERE d.id = :id"),
    @NamedQuery(name = "Dividends.findByExDate", query = "SELECT d FROM Dividends d WHERE d.exDate = :exDate"),
    @NamedQuery(name = "Dividends.findByPayDate", query = "SELECT d FROM Dividends d WHERE d.payDate = :payDate"),
    @NamedQuery(name = "Dividends.findByAmount", query = "SELECT d FROM Dividends d WHERE d.amount = :amount"),
    @NamedQuery(name = "Dividends.findByFranking", query = "SELECT d FROM Dividends d WHERE d.franking = :franking"),
    @NamedQuery(name = "Dividends.findByCumdDate", query = "SELECT d FROM Dividends d WHERE d.cumdDate = :cumdDate")})
public class Dividends implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "exDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exDate;
    @Basic(optional = false)
    @Column(name = "payDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "franking")
    private Double franking;
    @Column(name = "cumdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cumdDate;
    @JoinColumn(name = "Stock_id", referencedColumnName = "id")
    @ManyToOne
    private Stock stock;

    public Dividends() {
    }

    public Dividends(Integer id) {
        this.id = id;
    }

    public Dividends(Integer id, Date exDate, Date payDate) {
        this.id = id;
        this.exDate = exDate;
        this.payDate = payDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getExDate() {
        return exDate;
    }

    public void setExDate(Date exDate) {
        this.exDate = exDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFranking() {
        return franking;
    }

    public void setFranking(Double franking) {
        this.franking = franking;
    }

    public Date getCumdDate() {
        return cumdDate;
    }

    public void setCumdDate(Date cumdDate) {
        this.cumdDate = cumdDate;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stockid) {
        this.stock = stockid;
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
        if (!(object instanceof Dividends)) {
            return false;
        }
        Dividends other = (Dividends) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tintuna.stockfx.persistence.Dividends[ id=" + id + " ]";
    }
    
}
