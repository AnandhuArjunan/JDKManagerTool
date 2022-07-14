package com.anandhuarjunan.workspacetool.persistance.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="JAVA_ENV")
public class JavaEnv {

	@Id
    @Column(name="JE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
    @Column(name="JE_NAME")
	private String name;
    @Column(name="JE_COMPANY")
 	private Double company;
    @Column(name="JE_VERSION")
 	private String version;
    @Column(name="JE_EXECUTABLE_LOC")
 	private String executableLoc;
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
	public Double getCompany() {
		return company;
	}
	public void setCompany(Double company) {
		this.company = company;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getExecutableLoc() {
		return executableLoc;
	}
	public void setExecutableLoc(String executableLoc) {
		this.executableLoc = executableLoc;
	}




}
