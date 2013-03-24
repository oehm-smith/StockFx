/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tintuna.stockfx.persistence;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "shevent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShEvent.findAll", query = "SELECT s FROM ShEvent s"),
    @NamedQuery(name = "ShEvent.findById", query = "SELECT s FROM ShEvent s WHERE s.id = :id"),
    @NamedQuery(name = "ShEvent.findByEvent", query = "SELECT s FROM ShEvent s WHERE s.event = :event")})
public class ShEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "event")
    private String event;
    @OneToMany(mappedBy = "sHEvent")
    private Collection<StockHolding> stockholdingCollection;

    public ShEvent() {
    }

    public ShEvent(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @XmlTransient
    public Collection<StockHolding> getStockholdingCollection() {
        return stockholdingCollection;
    }

    public void setStockholdingCollection(Collection<StockHolding> stockholdingCollection) {
        this.stockholdingCollection = stockholdingCollection;
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
        if (!(object instanceof ShEvent)) {
            return false;
        }
        ShEvent other = (ShEvent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tintuna.stockfx.persistence.Shevent[ id=" + id + " ]";
    }
    
}
