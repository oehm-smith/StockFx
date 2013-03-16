/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tintuna.stockfx.persistence;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brooke Smith brooke@tintuna.org
 */
@Entity
@Table(name = "motleyfool")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Motleyfool.findAll", query = "SELECT m FROM Motleyfool m"),
    @NamedQuery(name = "Motleyfool.findById", query = "SELECT m FROM Motleyfool m WHERE m.id = :id"),
    @NamedQuery(name = "Motleyfool.findByDate", query = "SELECT m FROM Motleyfool m WHERE m.date = :date"),
    @NamedQuery(name = "Motleyfool.findByMfInfoTbd", query = "SELECT m FROM Motleyfool m WHERE m.mfInfoTbd = :mfInfoTbd")})
public class Motleyfool implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    private String date;
    @Column(name = "MF_INFO_TBD")
    private String mfInfoTbd;
    @JoinColumn(name = "Stock_id", referencedColumnName = "id")
    @ManyToOne
    private Stock stockid;

    public Motleyfool() {
    }

    public Motleyfool(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMfInfoTbd() {
        return mfInfoTbd;
    }

    public void setMfInfoTbd(String mfInfoTbd) {
        this.mfInfoTbd = mfInfoTbd;
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
        if (!(object instanceof Motleyfool)) {
            return false;
        }
        Motleyfool other = (Motleyfool) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tintuna.stockfx.persistence.Motleyfool[ id=" + id + " ]";
    }
    
}
