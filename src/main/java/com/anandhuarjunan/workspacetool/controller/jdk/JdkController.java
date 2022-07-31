package com.anandhuarjunan.workspacetool.controller.jdk;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.ResourcesLoader;
import com.anandhuarjunan.workspacetool.controller.NoDataController;
import com.anandhuarjunan.workspacetool.controller.ReloadableController;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;
import com.anandhuarjunan.workspacetool.util.Action;
import com.anandhuarjunan.workspacetool.util.AnimationUtils;
import com.anandhuarjunan.workspacetool.util.JavaEnvUtils;
import com.anandhuarjunan.workspacetool.util.Util;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXContextMenuItem;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
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

public class JdkController implements Initializable {

	private final ToggleGroup toggleGroup;

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


	    private Parent installedJdkNode = null;
	    private Parent downloadAndInstallView = null;


		protected ExecutorService executorService = Executors.newSingleThreadExecutor();

		protected InstalledJdkController installedJdkController = null;
		protected DownloadAndInstallJdkController downloadAndInstallJdkController = null;


	   public JdkController() {
		   this.toggleGroup = new ToggleGroup();
		   ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
		   installedJdkController = new InstalledJdkController(this);
		   downloadAndInstallJdkController = new DownloadAndInstallJdkController(this);

	   }




	@Override
	public void initialize(URL location, ResourceBundle resources) {


		try {
			initateViews();
			setInstalledJdkView();
			setControlsAndEvents();
			fillSearchCategories();
			setJavaHomeDetails();
			clearSearchBtn.setOnAction(ev->onSearchAction());
			searchBtn.setOnAction(ev->onSearchAction());
			onSearchClearAction();

		} catch (Exception e) {
			e.printStackTrace();
		}




	}

	private void onSearchClearAction() {
			installedJdkController.ides.getChildren().clear();
			installedJdkController.ides.getChildren().addAll(installedJdkController.allLocalJdkList);

	}

	private void onSearchAction() {
			onSearchClearAction();
			if(!StringUtils.isEmpty(searchBox.getText())) {
				installedJdkController.filterJdks(searchBox.getText(),categoryDropDown.getSelectionModel().getSelectedItem());
			}
	}




	private void fillSearchCategories() {
		categoryDropDown.getItems().addAll("Version","Vendor");
		categoryDropDown.getSelectionModel().selectFirst();

	}




	private void initateViews() throws IOException {
		installedJdkNode = Util.loadFxml("fxml/jdk/InstalledJdk.fxml",installedJdkController);
		downloadAndInstallView = Util.loadFxml("fxml/jdk/DownloadAndInstallJdk.fxml",downloadAndInstallJdkController);

	}


	private void setInstalledJdkView() {
		installedJdk.setSelected(true);
		contentPane.setContent(installedJdkNode);
	}




	private void setControlsAndEvents() {
		download.setToggleGroup(toggleGroup);
		settings.setToggleGroup(toggleGroup);
		installedJdk.setToggleGroup(toggleGroup);
		installedJdk.setOnAction(ev->setInstalledJdkView());
		download.setOnAction(ev->setDownloadAndInstallView());

	}


	private void setDownloadAndInstallView() {
		contentPane.setContent(downloadAndInstallView);

	}




	protected void setJavaHomeDetails() throws Exception {

		Map<String,String> javaDetails = JavaEnvUtils.getJavaEnvDetails();
		javaVersion.setText(javaDetails.get(JavaEnvUtils.JRE_VERSION).trim());
		javacVersion.setText(javaDetails.get(JavaEnvUtils.JDK_VERSION).trim());
		vendor.setText(javaDetails.get(JavaEnvUtils.VENDOR).trim());




	}




}
