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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "indexes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Indexes.findAll", query = "SELECT i FROM Indexes i"),
    @NamedQuery(name = "Indexes.findById", query = "SELECT i FROM Indexes i WHERE i.id = :id"),
    @NamedQuery(name = "Indexes.findByUrl", query = "SELECT i FROM Indexes i WHERE i.url = :url"),
    @NamedQuery(name = "Indexes.findByName", query = "SELECT i FROM Indexes i WHERE i.name = :name")})
public class Indexes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "url")
    private String url;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "indexesid")
    private Collection<Index> indexCollection;
    @JoinColumn(name = "Exchange_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Exchange exchangeid;

    public Indexes() {
    }

    public Indexes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Index> getIndexCollection() {
        return indexCollection;
    }

    public void setIndexCollection(Collection<Index> indexCollection) {
        this.indexCollection = indexCollection;
    }

    public Exchange getExchangeid() {
        return exchangeid;
    }

    public void setExchangeid(Exchange exchangeid) {
        this.exchangeid = exchangeid;
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
        if (!(object instanceof Indexes)) {
            return false;
        }
        Indexes other = (Indexes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tintuna.stockfx.persistence.Indexes[ id=" + id + " ]";
    }
    
}
