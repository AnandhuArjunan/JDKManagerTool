package com.anandhuarjunan.workspacetool.controller.workspaces;

import java.net.URL;
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
import com.anandhuarjunan.workspacetool.persistance.models.KvStrSettings;
import com.anandhuarjunan.workspacetool.persistance.models.Workspaces;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class WorkspaceBlock implements Initializable{
	@FXML
    private Label wlocation;

    @FXML
    private Label name;

    private Workspaces workspaces;

    public WorkspaceBlock(Workspaces workspaces) {
    	this.workspaces = workspaces;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(Objects.nonNull(workspaces)) {
			name.setText(workspaces.getName());
			wlocation.setText(workspaces.getLocation());
		}

	}
}
