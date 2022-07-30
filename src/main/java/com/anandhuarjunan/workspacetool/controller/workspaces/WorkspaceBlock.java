package com.anandhuarjunan.workspacetool.controller.workspaces;

import java.net.URL;
import java.util.ResourceBundle;

import com.anandhuarjunan.workspacetool.controller.common.NormalNameLocBlockController;
import com.anandhuarjunan.workspacetool.persistance.models.Workspaces;

import javafx.fxml.Initializable;

public class WorkspaceBlock extends NormalNameLocBlockController implements Initializable{


    private Workspaces workspaces;

    public WorkspaceBlock(Workspaces workspaces) {
    	super(workspaces.getName(), workspaces.getLocation(), workspaces.getIde().getIcon());
    	this.workspaces = workspaces;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		addStyleSheet("style/blocks/workspaceblock.css");


	}
}
