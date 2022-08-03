package com.anandhuarjunan.workspacetool.views.choosedir;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StatusBlockPresenter implements Initializable {


	   @FXML
	    private Label foundNo;

	    @FXML
	    private Label name;

	    String strNo = null;
	    String strname = null;


	public StatusBlockPresenter(String no,String name) {
		this.strNo = no;
		this.strname = name;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		foundNo.setText(strNo);
		name.setText(strname);

	}

}
