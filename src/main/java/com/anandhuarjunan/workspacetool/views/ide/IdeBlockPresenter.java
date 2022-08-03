package com.anandhuarjunan.workspacetool.views.ide;

import java.net.URL;
import java.util.ResourceBundle;

import com.anandhuarjunan.workspacetool.persistance.models.WindowsIDE;
import com.anandhuarjunan.workspacetool.views.common.NormalNameLocBlockPresenter;

import javafx.fxml.Initializable;

public class IdeBlockPresenter extends NormalNameLocBlockPresenter implements Initializable{



	 private WindowsIDE windowsIDE;
	public IdeBlockPresenter(WindowsIDE windowsIDE) {
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
