package com.anandhuarjunan.workspacetool.views.choosedir;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.anandhuarjunan.workspacetool.util.SimpleViewSwitcher;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StatusBlockPresenter implements Initializable {
    @FXML
    private MFXButton viewResults;

	   @FXML
	    private Label foundNo;

	    @FXML
	    private Label name;

	    @Inject
	    private String fileFound = null;

	    @Inject
		private SimpleViewSwitcher viewSwitcher;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		foundNo.setText(fileFound);
		viewResults.setOnAction(ev->viewSwitcher.switchToAndEnableButton(1));
	}
}
