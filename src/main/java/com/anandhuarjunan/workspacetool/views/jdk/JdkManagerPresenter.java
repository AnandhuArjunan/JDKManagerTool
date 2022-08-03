package com.anandhuarjunan.workspacetool.views.jdk;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.airhacks.afterburner.views.FXMLView;
import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.ResourcesLoader;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;
import com.anandhuarjunan.workspacetool.util.Action;
import com.anandhuarjunan.workspacetool.util.AnimationUtils;
import com.anandhuarjunan.workspacetool.util.JFXUtils;
import com.anandhuarjunan.workspacetool.util.JavaEnvUtils;
import com.anandhuarjunan.workspacetool.util.Util;
import com.anandhuarjunan.workspacetool.views.NoDataPresenter;
import com.anandhuarjunan.workspacetool.views.Reloadable;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXContextMenuItem;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class JdkManagerPresenter implements Initializable {

		private  ToggleGroup toggleGroup;
		@FXML
	 	protected MFXScrollPane contentPane;
	    @FXML
	    private ToggleButton download;
	    @FXML
	    private ToggleButton installedJdk;
	    @FXML
	    protected Label javaVersion;
	    @FXML
	    protected Label javacVersion;
	    @FXML
	    protected BorderPane mainPane;
	    @FXML
	    private ToggleButton settings;
	    @FXML
	    protected Label vendor;
	    @FXML
	    private ComboBox<String> categoryDropDown;
	    @FXML
	    private MFXButton clearSearchBtn;
	    @FXML
	    private TextField searchBox;
	    @FXML
	    private MFXButton searchBtn;
	    @FXML
	    private HBox headerBlock;
	    @Inject
	    private ExecutorService executorService = null;
	    @Inject
	    private InstalledJdkView installedJdkView = null;
	    @Inject
	    private DownloadAndInstallJdkView downloadAndInstallJdkView = null;


	 public JdkManagerPresenter() {
		 toggleGroup = new ToggleGroup();


	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			initateViews();
			setInstalledJdkView();
			setControlsAndEvents();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void initateViews()  {
			installedJdk.setSelected(true);
			setInstalledJdkView();
			installedJdk.setOnAction(ev->setInstalledJdkView());
			download.setOnAction(ev->setDownloadJdkView());



	}

	private void setInstalledJdkView() {
		InstalledJdkPresenter installedJdkPresenter = (InstalledJdkPresenter)installedJdkView.getPresenter();
		Parent headerNode = installedJdkPresenter.getHeaderView().getView();
		HBox.setHgrow(headerNode,Priority.ALWAYS );
		addToHeaderBlock(headerNode);
		contentPane.setContent(installedJdkView.getView());

	}
	private void setDownloadJdkView() {
		DownloadAndInstallJdkPresenter downloadAndInstallJdkPresenter = (DownloadAndInstallJdkPresenter)downloadAndInstallJdkView.getPresenter();
		Parent headerNode = downloadAndInstallJdkPresenter.getHeaderView().getView();
		HBox.setHgrow(headerNode,Priority.ALWAYS );
		addToHeaderBlock(headerNode);
		contentPane.setContent(downloadAndInstallJdkView.getView());
	}

	private void addToHeaderBlock(Node headerNode) {
		if(headerBlock.getChildren().size()==2) {
			headerBlock.getChildren().add(headerNode);
		}else {
			headerBlock.getChildren().set(2,headerNode);
		}
	}


	private void setControlsAndEvents() {
		ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
		JFXUtils.addtoToggleGroup(toggleGroup, download,settings,installedJdk);
	}







}
