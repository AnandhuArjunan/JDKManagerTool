package com.anandhuarjunan.workspacetool.controller.jdk;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class JdkBlock implements Initializable{

	@FXML
    private Label name;

    @FXML
    private Label wlocation;
	private JavaEnv javaEnv = null;

	public JdkBlock(JavaEnv javaEnv) {
		this.javaEnv  = javaEnv;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(Objects.nonNull(javaEnv)) {
			name.setText(javaEnv.getName());
			wlocation.setText(javaEnv.getExecutableLoc());
		}
	}

}
