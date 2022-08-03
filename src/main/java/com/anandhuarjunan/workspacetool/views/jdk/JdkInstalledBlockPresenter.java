package com.anandhuarjunan.workspacetool.views.jdk;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.anandhuarjunan.workspacetool.AppException;
import com.anandhuarjunan.workspacetool.constants.CommonEnvHomes;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;
import com.anandhuarjunan.workspacetool.util.Action;
import com.anandhuarjunan.workspacetool.util.EnvironmentVariableUtils;
import com.anandhuarjunan.workspacetool.util.Toast;
import com.anandhuarjunan.workspacetool.util.Util;
import com.anandhuarjunan.workspacetool.views.Reloadable;
import com.anandhuarjunan.workspacetool.views.choosedir.ChooseDirectoryPresenter.SyncDirectory;
import com.anandhuarjunan.workspacetool.views.common.NormalNameLocBlockPresenter;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.controls.MFXSimpleNotification;
import io.github.palexdev.materialfx.dialogs.MFXDialogs;
import io.github.palexdev.materialfx.enums.NotificationPos;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import io.github.palexdev.materialfx.notifications.MFXNotificationSystem;
import io.github.palexdev.materialfx.notifications.base.INotification;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class JdkInstalledBlockPresenter implements Initializable{

    @FXML
    private VBox box;

    @FXML
    private VBox buttonGroup;

    @FXML
    private MFXButton delete;

    @FXML
    private ImageView icon;

    @FXML
    private MFXButton locateInExplorer;

    @FXML
    private BorderPane mainPane;
    @FXML
    private Label currentStatus;
    @FXML
    private MFXFontIcon tickMark;

    @FXML
    private MFXProgressBar progressBar;
    @FXML
    private Label name;

    @FXML
    private MFXButton setdefault;

    @FXML
    private Label vendor;

    @FXML
    private Label wlocation;


	private boolean isDefault = false;

	@FXML
	private HBox currentJavaHomeFlag;

	private StatusBarHandler statusBarHandler;
	@Inject
	protected JavaEnv javaEnvModel = null;

	@Inject
	private Action afterSetDefaultAction = null;

	@Inject
	private Stage stage;

	@Inject
	private ExecutorService executorService;



	public JdkInstalledBlockPresenter() {
		statusBarHandler = new StatusBarHandler();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statusBarHandler.disableStatusBar();
		name.setText("Java "+javaEnvModel.getVersion().trim());
		wlocation.setText(javaEnvModel.getJavaHome().trim());
		vendor.setText(javaEnvModel.getCompany().trim());
		try {
			checkIfCurrentJavaHome();
		} catch (IOException e) {
			e.printStackTrace();
		}


		setdefault.setOnAction(ev->makeDefault());

	}
	private void makeDefault() {
		Runnable bgThread = () -> {
			try {
				Platform.runLater(()->{
					statusBarHandler.enableProgressBlock();
				});
				setJavaHome();
				Platform.runLater(()->{
					afterSetDefaultAction.action();
					statusBarHandler.setDefaultHomeBlock();
					 //Toast.makeText(stage, "\"Done ! Re open your Command Prompt to affect the Environment Change!\"", 5000, 0, 0);
				});
			} catch (IOException | InterruptedException | AppException e) {
			    Thread.currentThread().interrupt();
			    //statusManager.setError("Something Went Wrong!").show();
			}
		};
		executorService.execute(bgThread);
	}






	private void checkIfCurrentJavaHome() throws IOException {
		EnvironmentVariableUtils environmentUtils = new EnvironmentVariableUtils();
		String javaPathFromEnv = environmentUtils.getEnvValue(CommonEnvHomes.JAVA_HOME);
		if(!StringUtils.isEmpty(javaPathFromEnv) && !StringUtils.isEmpty(javaEnvModel.getJavaHome())&&
				javaPathFromEnv.trim().equalsIgnoreCase(javaEnvModel.getJavaHome().trim())) {
			setdefault.setDisable(true);
			statusBarHandler.setDefaultHomeBlock();
			setDefault(true);
		}else {
			setdefault.setDisable(false);
			setDefault(false);
		}
	}
	private void setJavaHome() throws IOException, InterruptedException, AppException {
		EnvironmentVariableUtils environmentUtils = new EnvironmentVariableUtils();
		if(environmentUtils.setEnvVariable(CommonEnvHomes.JAVA_HOME, javaEnvModel.getJavaHome()) == 1) {
			throw new AppException();
		}
	}



	public boolean isDefault() {
		return isDefault;
	}



	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}



	class StatusBarHandler{


		public void disableStatusBar() {
			mainPane.setTop(null);
		}
		public void enableStatusBar() {
			mainPane.setTop(currentJavaHomeFlag);
		}

		public void enableProgressBlock() {
			enableStatusBar();
			currentJavaHomeFlag.getChildren().clear();
			currentJavaHomeFlag.getChildren().add(currentStatus);
			currentJavaHomeFlag.getChildren().add(progressBar);
			currentStatus.setText("Setting..");
		}
		public void setDefaultHomeBlock() {
			enableStatusBar();
			currentJavaHomeFlag.getChildren().clear();
			currentJavaHomeFlag.getChildren().add(tickMark);
			currentJavaHomeFlag.getChildren().add(currentStatus);
			currentStatus.setText("Current Java Home");
		}


	}

}
