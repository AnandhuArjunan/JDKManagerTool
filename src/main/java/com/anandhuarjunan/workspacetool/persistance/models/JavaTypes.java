package com.anandhuarjunan.workspacetool.persistance.models;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="JAVA_TYPES")
public class JavaTypes {
	@Id
    @Column(name="JT_ID")
	private String id;
    @Column(name="JT_NAME")
	private String name;
    @Column(name="JT_ICON")
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
	public Blob getIcon() {
		return icon;
	}
	public void setIcon(Blob icon) {
		this.icon = icon;
	}


}
