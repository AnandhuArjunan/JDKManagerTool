package com.anandhuarjunan.workspacetool.persistance.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WINDOWS_IDE")
public class WindowsIDE {

	@Id
    @Column(name="WI_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
    @Column(name="WI_NAME")
	private String name;
    @Column(name="WI_VERSION")
 	private Double version;
    @Column(name="WI_EXEC_LOCATION")
 	private String location;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getVersion() {
		return version;
	}
	public void setVersion(Double version) {
		this.version = version;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}


}
