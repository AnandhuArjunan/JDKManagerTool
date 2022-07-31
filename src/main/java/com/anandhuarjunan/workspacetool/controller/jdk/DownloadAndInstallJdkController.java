package com.anandhuarjunan.workspacetool.controller.jdk;

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
import com.anandhuarjunan.workspacetool.controller.NoDataController;
import com.anandhuarjunan.workspacetool.controller.ReloadableController;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class DownloadAndInstallJdkController implements Initializable,ReloadableController,NoDataController {

	 JdkController controller = null;

	public DownloadAndInstallJdkController(JdkController controller) {

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
