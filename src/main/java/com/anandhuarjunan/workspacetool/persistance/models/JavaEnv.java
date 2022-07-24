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
@Table(name="JAVA_ENV")
public class JavaEnv {

	@Id
    @Column(name="JE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
    @Column(name="JE_COMPANY")
 	private String company;
    @Column(name="JE_VERSION")
 	private String version;
    @Column(name="JE_EXECUTABLE_LOC")
 	private String executableLoc;


    @Column(name="JE_JAVA_HOME")
 	private String javaHome;

    @OneToOne
	@JoinColumn(name = "JE_JT_ID")
 	private JavaTypes javaType;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
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
	public String getJavaHome() {
		return javaHome;
	}
	public void setJavaHome(String javaHome) {
		this.javaHome = javaHome;
	}
	public JavaTypes getJavaType() {
		return javaType;
	}
	public void setJavaType(JavaTypes javaType) {
		this.javaType = javaType;
	}




}
