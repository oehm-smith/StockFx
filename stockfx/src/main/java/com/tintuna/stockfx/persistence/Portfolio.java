package com.tintuna.stockfx.persistence;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@NamedQueries({
    @NamedQuery(name = "Portfolio.findAll", query = "SELECT p FROM Portfolio p"),
    @NamedQuery(name = "Portfolio.findById", query = "SELECT p FROM Portfolio p WHERE p.id = :id"),
    @NamedQuery(name = "Portfolio.findByName", query = "SELECT p FROM Portfolio p WHERE p.name = :name"),
    @NamedQuery(name = "Portfolio.findByType", query = "SELECT p FROM Portfolio p WHERE p.name = :name")})
@XmlRootElement
public class Portfolio {
	private long id;

	private StringProperty name;
	private StringProperty type;

	public Portfolio() {
		
	}
	
	public Portfolio(String name, String type) {
		setName(name);
		setType(type);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		if (name == null) {
			return "";
		}
		return name.get();
	}

	public void setName(String name) {
		getNameProperty().set(name);
	}

	public StringProperty getNameProperty() {
		if (name == null) {
			name = new SimpleStringProperty();
		}
		return name;
	}

	public String getType() {
		if (type == null) {
			return "";
		}
		return type.get();
	}

	public void setType(String type) {
		getTypeProperty().set(type);
	}

	public StringProperty getTypeProperty() {
		if (type == null) {
			type = new SimpleStringProperty();
		}
		return type;
	}
	
	public String toString() {
		return getName()+" : "+getType();
	}
}