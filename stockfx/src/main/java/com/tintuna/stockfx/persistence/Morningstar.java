/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tintuna.stockfx.persistence;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "morningstar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Morningstar.findAll", query = "SELECT m FROM Morningstar m"),
    @NamedQuery(name = "Morningstar.findById", query = "SELECT m FROM Morningstar m WHERE m.id = :id"),
    @NamedQuery(name = "Morningstar.findByDate", query = "SELECT m FROM Morningstar m WHERE m.date = :date"),
    @NamedQuery(name = "Morningstar.findByBuy", query = "SELECT m FROM Morningstar m WHERE m.buy = :buy"),
    @NamedQuery(name = "Morningstar.findByAccumulate", query = "SELECT m FROM Morningstar m WHERE m.accumulate = :accumulate"),
    @NamedQuery(name = "Morningstar.findByHold", query = "SELECT m FROM Morningstar m WHERE m.hold = :hold"),
    @NamedQuery(name = "Morningstar.findByReduce", query = "SELECT m FROM Morningstar m WHERE m.reduce = :reduce"),
    @NamedQuery(name = "Morningstar.findBySell", query = "SELECT m FROM Morningstar m WHERE m.sell = :sell")})
public class Morningstar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "buy")
    private Double buy;
    @Column(name = "accumulate")
    private Double accumulate;
    @Column(name = "hold")
    private Double hold;
    @Column(name = "reduce")
    private Double reduce;
    @Column(name = "sell")
    private Double sell;
    @JoinColumn(name = "Stock_id", referencedColumnName = "id")
    @ManyToOne
    private Stock stockid;

    public Morningstar() {
    }

    public Morningstar(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public Double getAccumulate() {
        return accumulate;
    }

    public void setAccumulate(Double accumulate) {
        this.accumulate = accumulate;
    }

    public Double getHold() {
        return hold;
    }

    public void setHold(Double hold) {
        this.hold = hold;
    }

    public Double getReduce() {
        return reduce;
    }

    public void setReduce(Double reduce) {
        this.reduce = reduce;
    }

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    public Stock getStockid() {
        return stockid;
    }

    public void setStockid(Stock stockid) {
        this.stockid = stockid;
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
        if (!(object instanceof Morningstar)) {
            return false;
        }
        Morningstar other = (Morningstar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tintuna.stockfx.persistence.Morningstar[ id=" + id + " ]";
    }
    
}
