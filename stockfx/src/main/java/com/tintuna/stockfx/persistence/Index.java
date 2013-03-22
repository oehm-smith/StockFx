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
@Table(name = "index")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Index.findAll", query = "SELECT i FROM Index i"),
    @NamedQuery(name = "Index.findById", query = "SELECT i FROM Index i WHERE i.id = :id"),
    @NamedQuery(name = "Index.findByDate", query = "SELECT i FROM Index i WHERE i.date = :date"),
    @NamedQuery(name = "Index.findByOpen", query = "SELECT i FROM Index i WHERE i.open = :open"),
    @NamedQuery(name = "Index.findByClose", query = "SELECT i FROM Index i WHERE i.close = :close"),
    @NamedQuery(name = "Index.findByLow", query = "SELECT i FROM Index i WHERE i.low = :low"),
    @NamedQuery(name = "Index.findByHigh", query = "SELECT i FROM Index i WHERE i.high = :high")})
public class Index implements Serializable {
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
    @JoinColumn(name = "Indexes_id", referencedColumnName = "id")
    @ManyToOne
    private Indexes indexesid;

    public Index() {
    }

    public Index(Integer id) {
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

    public Indexes getIndexesid() {
        return indexesid;
    }

    public void setIndexesid(Indexes indexesid) {
        this.indexesid = indexesid;
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
        if (!(object instanceof Index)) {
            return false;
        }
        Index other = (Index) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tintuna.stockfx.persistence.Index[ id=" + id + " ]";
    }
    
}
