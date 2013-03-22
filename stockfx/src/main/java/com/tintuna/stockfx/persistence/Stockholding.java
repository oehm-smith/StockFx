/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tintuna.stockfx.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brooke Smith brooke@tintuna.org
 */
@Entity
@Table(name = "stockholding")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stockholding.findAll", query = "SELECT s FROM Stockholding s"),
    @NamedQuery(name = "Stockholding.findById", query = "SELECT s FROM Stockholding s WHERE s.id = :id"),
    @NamedQuery(name = "Stockholding.findByDate", query = "SELECT s FROM Stockholding s WHERE s.date = :date"),
    @NamedQuery(name = "Stockholding.findByTransaction", query = "SELECT s FROM Stockholding s WHERE s.transaction = :transaction"),
    @NamedQuery(name = "Stockholding.findByBrokeridge", query = "SELECT s FROM Stockholding s WHERE s.brokeridge = :brokeridge"),
    @NamedQuery(name = "Stockholding.findByTax", query = "SELECT s FROM Stockholding s WHERE s.tax = :tax")})
public class Stockholding implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "transaction")
    private Integer transaction;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "brokeridge")
    private Double brokeridge;
    @Column(name = "tax")
    private Double tax;
    @JoinColumn(name = "SHEvent_id", referencedColumnName = "id")
    @ManyToOne
    private Shevent sHEventid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockHoldingid")
    private Collection<PortfolioStock> portfoliostockCollection;

    public Stockholding() {
    }

    public Stockholding(Integer id) {
        this.id = id;
    }

    public Stockholding(Integer id, Date date) {
        this.id = id;
        this.date = date;
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

    public Integer getTransaction() {
        return transaction;
    }

    public void setTransaction(Integer transaction) {
        this.transaction = transaction;
    }

    public Double getBrokeridge() {
        return brokeridge;
    }

    public void setBrokeridge(Double brokeridge) {
        this.brokeridge = brokeridge;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Shevent getSHEventid() {
        return sHEventid;
    }

    public void setSHEventid(Shevent sHEventid) {
        this.sHEventid = sHEventid;
    }

    @XmlTransient
    public Collection<PortfolioStock> getPortfoliostockCollection() {
        return portfoliostockCollection;
    }

    public void setPortfoliostockCollection(Collection<PortfolioStock> portfoliostockCollection) {
        this.portfoliostockCollection = portfoliostockCollection;
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
        if (!(object instanceof Stockholding)) {
            return false;
        }
        Stockholding other = (Stockholding) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tintuna.stockfx.persistence.Stockholding[ id=" + id + " ]";
    }
    
}
