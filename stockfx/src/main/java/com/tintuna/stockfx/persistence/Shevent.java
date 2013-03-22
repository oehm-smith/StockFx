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
    @NamedQuery(name = "Shevent.findAll", query = "SELECT s FROM Shevent s"),
    @NamedQuery(name = "Shevent.findById", query = "SELECT s FROM Shevent s WHERE s.id = :id"),
    @NamedQuery(name = "Shevent.findByEvent", query = "SELECT s FROM Shevent s WHERE s.event = :event")})
public class Shevent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "event")
    private String event;
    @OneToMany(mappedBy = "sHEventid")
    private Collection<Stockholding> stockholdingCollection;

    public Shevent() {
    }

    public Shevent(Integer id) {
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
    public Collection<Stockholding> getStockholdingCollection() {
        return stockholdingCollection;
    }

    public void setStockholdingCollection(Collection<Stockholding> stockholdingCollection) {
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
        if (!(object instanceof Shevent)) {
            return false;
        }
        Shevent other = (Shevent) object;
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
