package com.anandhuarjunan.workspacetool.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.anandhuarjunan.workspacetool.UIResources;
import com.anandhuarjunan.workspacetool.constants.Constants;
import com.anandhuarjunan.workspacetool.constants.ReloadableViews;
import com.anandhuarjunan.workspacetool.controller.choosedir.ChooseDirectoryController;
import com.anandhuarjunan.workspacetool.persistance.models.KvStrSettings;
import com.anandhuarjunan.workspacetool.util.Action;
import com.anandhuarjunan.workspacetool.util.AnimationUtils;
import com.anandhuarjunan.workspacetool.util.Util;

import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController implements Initializable {

	private double xOffset;
	private double yOffset;
	private final ToggleGroup toggleGroup;

	@FXML
	private MFXFontIcon close;

	@FXML
	private StackPane contentPane;

	@FXML
	private MFXFontIcon maximize;

	@FXML
	private MFXFontIcon minimize;

	@FXML
	private VBox navBar;

	@FXML
	private HBox wHeader;

	@FXML
	private MFXScrollPane scrollPane;

	private Stage stage;

    @FXML
    private Label username;

    @FXML
    private HBox statusBar;

    @FXML
    private MFXFontIcon statusIcon;

    @FXML
    private Label statusText;

    @FXML
    private BorderPane root;

	private Parent choose = null;
	private FXMLLoader ideNodeLoader = null;
	private FXMLLoader jdkNodeLoader = null;
	private FXMLLoader workNodeLoader = null;

	private Parent ideNode = null;
	private Parent jdkNode = null;
	private Parent workNode = null;
	private StatusManager statusManager = null;
	public MainController(Stage stage) {
		this.stage = stage;
		this.toggleGroup = new ToggleGroup();
		ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
		statusManager=new StatusManager();
		UIResources.getInstance().setStatusManager(statusManager);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statusManager.hide();
		close.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
		maximize.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

			stage.setMaximized(!stage.isMaximized());

		});
		minimize.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			stage.setIconified(true);
		});

		wHeader.setOnMousePressed(event -> {
			xOffset = stage.getX() - event.getScreenX();
			yOffset = stage.getY() - event.getScreenY();
		});
		wHeader.setOnMouseDragged(event -> {
			stage.setX(event.getScreenX() + xOffset);
			stage.setY(event.getScreenY() + yOffset);
		});

		username.setText(System.getProperty("user.name"));

		ScrollUtils.addSmoothScrolling(scrollPane);
		try {
			initializeLoader();
		} catch (IOException e) {

			Alert a = new Alert(AlertType.NONE);
			a.setTitle("dd");
			a.setContentText(e.getMessage());
			a.show();
		}


	}

	private void initializeLoader() throws IOException {
		ToggleButton chooseDir = createToggle("mfx-search", "Choose Root Directory");
		ToggleButton workspace = createToggle("mfx-circle-dot", "Workspaces");

		ToggleButton ide = createToggle("mfx-circle-dot", "IDE");
		ToggleButton jdk = createToggle("mfx-circle-dot", "JDK Manager");

		navBar.getChildren().add(chooseDir);
		navBar.getChildren().add(jdk);
		navBar.getChildren().add(workspace);

		navBar.getChildren().add(ide);



			 ChooseDirectoryController chooseDirectoryController = new ChooseDirectoryController();
			 choose = Util.loadFxml("fxml/ChooseDirectory.fxml",chooseDirectoryController);
			ideNodeLoader = Util.loadFxmlLoader("fxml/idemanager.fxml");
			 jdkNodeLoader = Util.loadFxmlLoader("fxml/jdkmanager.fxml");
			 workNodeLoader = Util.loadFxmlLoader("fxml/workspacemanager.fxml");

			ideNode =  ideNodeLoader.load();
			jdkNode = jdkNodeLoader.load();
			workNode = workNodeLoader.load();
			contentPane.getChildren().add(choose);
			contentPane.getChildren().add(ideNode);
			contentPane.getChildren().add(jdkNode);
			contentPane.getChildren().add(workNode);

			chooseDirectoryController.addDependencyController(ReloadableViews.IDE, ideNodeLoader.getController());
			chooseDirectoryController.addDependencyController(ReloadableViews.JDK, jdkNodeLoader.getController());
			chooseDirectoryController.addDependencyController(ReloadableViews.WORKSPACE, workNodeLoader.getController());





		chooseDir.setOnAction(e->showChooseDir());
		workspace.setOnAction(e->showWorkspaces());
		ide.setOnAction(e->showIde());
		jdk.setOnAction(e->showJdk());

		if(Util.isDataPresent(KvStrSettings.class, Constants.ROOT_LOCATION)) {
			showWorkspaces();
			workspace.setSelected(true);
		}else {
			showChooseDir();
			chooseDir.setSelected(true);
		}

	}


	private void showChooseDir() {
		choose.setVisible(true);
		ideNode.setVisible(false);
		jdkNode.setVisible(false);
		workNode.setVisible(false);
	}

	private void showWorkspaces() {
		choose.setVisible(false);
		ideNode.setVisible(false);
		jdkNode.setVisible(false);
		workNode.setVisible(true);
	}

	private void showIde() {
		choose.setVisible(false);
		ideNode.setVisible(true);
		jdkNode.setVisible(false);
		workNode.setVisible(false);
	}
	private void showJdk() {
		choose.setVisible(false);
		ideNode.setVisible(false);
		jdkNode.setVisible(true);
		workNode.setVisible(false);
	}

	private ToggleButton createToggle(String icon, String text) {
		MFXIconWrapper wrapper = new MFXIconWrapper(icon, 24, 32);

		MFXRectangleToggleNode toggleNode = new MFXRectangleToggleNode(text, wrapper);
		toggleNode.setAlignment(Pos.CENTER_LEFT);
		toggleNode.setMaxWidth(Double.MAX_VALUE);
		toggleNode.setToggleGroup(toggleGroup);

		return toggleNode;
	}

	public void shutDown(WindowEvent ev) {
		Platform.exit();

	}

	public class StatusManager{
		public StatusManager setError(String errorMessage) {
			Platform.runLater(()->{
				statusBar.setStyle("-fx-background-color: -errorSuccess-gradient");
				statusText.setText(errorMessage);
				statusIcon.setDescription("mfx-x-circle");
			});

			return this;
		}


		public StatusManager setSuccess(String successMessage) {
			Platform.runLater(()->{
				statusBar.setStyle("-fx-background-color: -statusSuccess-gradient");
				statusText.setText(successMessage);
				statusIcon.setDescription("mfx-check-circle");

			});

			return this;
		}

		public synchronized void show() {

			Action hideAction = ()->root.setBottom(statusBar);
			AnimationUtils.addAnimating(hideAction, AnimationUtils.fadeInAnimationSupplier(statusBar));

			Timer timer = new Timer();
			TimerTask task = new TimerTask()
			{
			        public void run()
			        {
			        	Platform.runLater(()->{
			    			AnimationUtils.addAnimating(hideAction, AnimationUtils.fadeOutAnimationSupplier(statusBar));
			    			root.setBottom(null);
			        	});

			        }

			};
			timer.schedule(task,10000l);
		}

		public synchronized void hide() {
			Platform.runLater(()->{
				root.setBottom(null);
			});
		}
	}

}
