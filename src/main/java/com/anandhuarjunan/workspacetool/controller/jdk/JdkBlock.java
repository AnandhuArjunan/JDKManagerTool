package com.anandhuarjunan.workspacetool.controller.jdk;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.StringUtils;

import com.anandhuarjunan.workspacetool.AppException;
import com.anandhuarjunan.workspacetool.constants.CommonEnvHomes;
import com.anandhuarjunan.workspacetool.controller.ReloadableController;
import com.anandhuarjunan.workspacetool.controller.choosedir.ChooseDirectoryController.SyncDirectory;
import com.anandhuarjunan.workspacetool.controller.common.NormalNameLocBlockController;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;
import com.anandhuarjunan.workspacetool.util.EnvironmentVariableUtils;
import com.anandhuarjunan.workspacetool.util.Util;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.controls.MFXSimpleNotification;
import io.github.palexdev.materialfx.enums.NotificationPos;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import io.github.palexdev.materialfx.notifications.MFXNotificationSystem;
import io.github.palexdev.materialfx.notifications.base.INotification;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class JdkBlock implements Initializable{

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
	private JavaEnv javaEnv = null;

	@FXML
	private HBox currentJavaHomeFlag;

	private StatusBarHandler statusBarHandler;

	private  JdkController jdkController = null;

	public JdkBlock(JavaEnv javaEnv, JdkController jdkController) {
		this.javaEnv  = javaEnv;
		this.statusBarHandler = new StatusBarHandler();
		this.jdkController = jdkController;
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statusBarHandler.disableStatusBar();
		name.setText("Java "+javaEnv.getVersion().trim());
		wlocation.setText(javaEnv.getJavaHome().trim());
		vendor.setText(javaEnv.getCompany().trim());

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
					jdkController.reload();
					jdkController.javacVersion.setText(javaEnv.getVersion());
					jdkController.javaVersion.setText(javaEnv.getVersion());
					jdkController.vendor.setText(javaEnv.getCompany());

				});
			} catch (IOException | InterruptedException | AppException e) {

			}
		};
		jdkController.executorService.execute(bgThread);
	}






	private void checkIfCurrentJavaHome() throws IOException {
		EnvironmentVariableUtils environmentUtils = new EnvironmentVariableUtils();
		String javaPathFromEnv = environmentUtils.getEnvValue(CommonEnvHomes.JAVA_HOME);
		if(!StringUtils.isEmpty(javaPathFromEnv) && !StringUtils.isEmpty(javaEnv.getJavaHome())&&
				javaPathFromEnv.trim().equalsIgnoreCase(javaEnv.getJavaHome().trim())) {
			setdefault.setDisable(true);
			statusBarHandler.setDefaultHomeBlock();
		}else {
			setdefault.setDisable(false);
		}
	}
	private void setJavaHome() throws IOException, InterruptedException, AppException {
		EnvironmentVariableUtils environmentUtils = new EnvironmentVariableUtils();
		if(environmentUtils.setEnvVariable(CommonEnvHomes.JAVA_HOME, javaEnv.getJavaHome()) == 1) {
			throw new AppException();
		}
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
