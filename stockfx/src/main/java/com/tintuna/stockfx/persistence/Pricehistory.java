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
@Table(name = "pricehistory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pricehistory.findAll", query = "SELECT p FROM Pricehistory p"),
    @NamedQuery(name = "Pricehistory.findById", query = "SELECT p FROM Pricehistory p WHERE p.id = :id"),
    @NamedQuery(name = "Pricehistory.findByDate", query = "SELECT p FROM Pricehistory p WHERE p.date = :date"),
    @NamedQuery(name = "Pricehistory.findByOpen", query = "SELECT p FROM Pricehistory p WHERE p.open = :open"),
    @NamedQuery(name = "Pricehistory.findByClose", query = "SELECT p FROM Pricehistory p WHERE p.close = :close"),
    @NamedQuery(name = "Pricehistory.findByLow", query = "SELECT p FROM Pricehistory p WHERE p.low = :low"),
    @NamedQuery(name = "Pricehistory.findByHigh", query = "SELECT p FROM Pricehistory p WHERE p.high = :high"),
    @NamedQuery(name = "Pricehistory.findByVolume", query = "SELECT p FROM Pricehistory p WHERE p.volume = :volume")})
public class Pricehistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "open")
    private Double open;
    @Column(name = "close")
    private Double close;
    @Column(name = "low")
    private Double low;
    @Column(name = "high")
    private Double high;
    @Column(name = "volume")
    private String volume;
    @JoinColumn(name = "Stock_id", referencedColumnName = "id")
    @ManyToOne
    private Stock stockid;

    public Pricehistory() {
    }

    public Pricehistory(Integer id) {
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

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
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
        if (!(object instanceof Pricehistory)) {
            return false;
        }
        Pricehistory other = (Pricehistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tintuna.stockfx.persistence.Pricehistory[ id=" + id + " ]";
    }
    
}
