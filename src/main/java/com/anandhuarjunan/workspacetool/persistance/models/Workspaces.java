package com.anandhuarjunan.workspacetool.persistance.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="WORKSPACES")
public class Workspaces {

	@Id
    @Column(name="W_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
    @Column(name="W_LOCATION")
	private String location;
    @Column(name="W_NAME")
 	private String name;

    @OneToOne
	@JoinColumn(name = "W_IDE_ID")
 	private Ides ide;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Ides getIde() {
		return ide;
	}
	public void setIde(Ides ide) {
		this.ide = ide;
	}

}
