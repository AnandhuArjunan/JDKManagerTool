package com.anandhuarjunan.workspacetool.views.workspaces;

import java.net.URL;
import java.util.ResourceBundle;

import com.anandhuarjunan.workspacetool.persistance.models.Workspaces;
import com.anandhuarjunan.workspacetool.views.common.NormalNameLocBlockPresenter;

import javafx.fxml.Initializable;

public class WorkspaceBlockPresenter extends NormalNameLocBlockPresenter implements Initializable{


    private Workspaces workspaces;

    public WorkspaceBlockPresenter(Workspaces workspaces) {
    	super(workspaces.getName(), workspaces.getLocation(), workspaces.getIde().getIcon());
    	this.workspaces = workspaces;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		addStyleSheet("style/blocks/workspaceblock.css");


	}
}
