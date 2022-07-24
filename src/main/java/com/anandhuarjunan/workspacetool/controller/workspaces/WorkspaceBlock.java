package com.anandhuarjunan.workspacetool.controller.workspaces;

import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.controller.common.NormalNameLocBlockController;
import com.anandhuarjunan.workspacetool.persistance.models.KvStrSettings;
import com.anandhuarjunan.workspacetool.persistance.models.Workspaces;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

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
