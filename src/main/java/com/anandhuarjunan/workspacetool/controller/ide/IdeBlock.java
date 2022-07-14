package com.anandhuarjunan.workspacetool.controller.ide;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.anandhuarjunan.workspacetool.persistance.models.WindowsIDE;
import com.anandhuarjunan.workspacetool.persistance.models.Workspaces;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class IdeBlock implements Initializable{

    @FXML
    private Label name;

    @FXML
    private Label wlocation;

	 private WindowsIDE windowsIDE;
	public IdeBlock(WindowsIDE windowsIDE) {
		this.windowsIDE = windowsIDE;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(Objects.nonNull(windowsIDE)) {
			name.setText(windowsIDE.getName());
			wlocation.setText(windowsIDE.getLocation());
		}

	}

}
