package com.anandhuarjunan.workspacetool.views.choosedir;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StatusBlockPresenter implements Initializable {


	   @FXML
	    private Label foundNo;

	    @FXML
	    private Label name;

	    @Inject
	    String strname = null;
	    @Inject
	    String fileFound = null;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		foundNo.setText(fileFound);
		name.setText(strname);

	}

}
