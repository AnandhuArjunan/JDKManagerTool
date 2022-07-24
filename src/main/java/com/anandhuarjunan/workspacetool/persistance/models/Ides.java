package com.anandhuarjunan.workspacetool.persistance.models;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IDE_TYPES")
public class Ides {

	@Id
    @Column(name="IT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
    @Column(name="IT_NAME")
	private String name;
    @Column(name="IT_DEVELOPER")
 	private String developer;
    @Column(name="IT_VERSION")
 	private String version;
    @Column(name="IT_ICON")
 	private Blob icon;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Blob getIcon() {
		return icon;
	}
	public void setIcon(Blob icon) {
		this.icon = icon;
	}



}
