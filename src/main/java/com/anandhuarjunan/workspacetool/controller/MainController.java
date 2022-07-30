package com.anandhuarjunan.workspacetool.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.anandhuarjunan.workspacetool.constants.Constants;
import com.anandhuarjunan.workspacetool.constants.ReloadableViews;
import com.anandhuarjunan.workspacetool.controller.choosedir.ChooseDirectoryController;
import com.anandhuarjunan.workspacetool.persistance.models.KvStrSettings;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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

	private Parent choose = null;
	private FXMLLoader ideNodeLoader = null;
	private FXMLLoader jdkNodeLoader = null;
	private FXMLLoader workNodeLoader = null;

	private Parent ideNode = null;
	private Parent jdkNode = null;
	private Parent workNode = null;

	public MainController(Stage stage) {
		this.stage = stage;
		this.toggleGroup = new ToggleGroup();
		ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
		navBar.getChildren().add(workspace);

		navBar.getChildren().add(ide);
		navBar.getChildren().add(jdk);


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

}
