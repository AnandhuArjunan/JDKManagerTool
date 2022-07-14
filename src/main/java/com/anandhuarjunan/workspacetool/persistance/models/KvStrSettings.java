package com.anandhuarjunan.workspacetool.persistance.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="KV__STR_SETTINGS")
public class KvStrSettings {
	@Id
    @Column(name="KS_KEY")
	private String key;

    @Cascade(CascadeType.SAVE_UPDATE)
    @Column(name="KS_VALUE")
	private String value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
