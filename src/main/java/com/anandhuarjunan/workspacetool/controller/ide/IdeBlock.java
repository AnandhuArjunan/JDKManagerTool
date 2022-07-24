package com.anandhuarjunan.workspacetool.controller.ide;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.anandhuarjunan.workspacetool.controller.common.NormalNameLocBlockController;
import com.anandhuarjunan.workspacetool.persistance.models.WindowsIDE;
import com.anandhuarjunan.workspacetool.persistance.models.Workspaces;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class IdeBlock extends NormalNameLocBlockController implements Initializable{



	 private WindowsIDE windowsIDE;
	public IdeBlock(WindowsIDE windowsIDE) {
		super(windowsIDE.getIde().getName(), windowsIDE.getLocation(), windowsIDE.getIde().getIcon());
		this.windowsIDE = windowsIDE;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		addStyleSheet("style/blocks/ideblock.css");
		if(windowsIDE.getIde().getId().equalsIgnoreCase("ECLIPSE")) {
			addStyleSheet("style/blocks/ide/eclipse.css");
		}
	}

}
