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
@Table(name="WINDOWS_IDE")
public class WindowsIDE {

	@Id
    @Column(name="WI_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name="WI_EXEC_LOCATION")
 	private String location;
	   @OneToOne
		@JoinColumn(name = "WI_IT_ID")
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
	public Ides getIde() {
		return ide;
	}
	public void setIde(Ides ide) {
		this.ide = ide;
	}


}
