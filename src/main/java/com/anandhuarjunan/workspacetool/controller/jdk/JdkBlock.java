package com.anandhuarjunan.workspacetool.controller.jdk;

import java.net.URL;
import java.util.ResourceBundle;

import com.anandhuarjunan.workspacetool.controller.common.NormalNameLocBlockController;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class JdkBlock extends NormalNameLocBlockController implements Initializable{

	@FXML
    private Label name;

    @FXML
    private Label wlocation;
	private JavaEnv javaEnv = null;

	public JdkBlock(JavaEnv javaEnv) {
		super(javaEnv.getJavaType().getName()+" "+javaEnv.getVersion(), javaEnv.getExecutableLoc(),javaEnv.getJavaType().getIcon());
		this.javaEnv  = javaEnv;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	super.initialize(location, resources);		addStyleSheet("style/blocks/javablock.css");

	}

}
