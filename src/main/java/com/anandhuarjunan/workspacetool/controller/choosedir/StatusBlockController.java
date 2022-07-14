package com.anandhuarjunan.workspacetool.controller.choosedir;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StatusBlockController implements Initializable {


	   @FXML
	    private Label foundNo;

	    @FXML
	    private Label name;

	    String strNo = null;
	    String strname = null;


	public StatusBlockController(String no,String name) {
		this.strNo = no;
		this.strname = name;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		foundNo.setText(strNo);
		name.setText(strname);

	}

}
