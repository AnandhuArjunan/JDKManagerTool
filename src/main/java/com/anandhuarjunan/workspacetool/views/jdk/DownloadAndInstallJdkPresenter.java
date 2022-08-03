package com.anandhuarjunan.workspacetool.views.jdk;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.ResourcesLoader;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;
import com.anandhuarjunan.workspacetool.views.NoDataPresenter;
import com.anandhuarjunan.workspacetool.views.Reloadable;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class DownloadAndInstallJdkPresenter implements Initializable,Reloadable,NoDataPresenter {

	 JdkManagerPresenter controller = null;

	public DownloadAndInstallJdkPresenter(JdkManagerPresenter controller) {

		this.controller = controller;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadJdk();

	}

	@Override
	public void reload() {
		loadJdk();

	}

	public void loadJdk() {}
}
