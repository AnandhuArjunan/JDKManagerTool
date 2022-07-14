package com.anandhuarjunan.workspacetool.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.anandhuarjunan.binarytext.ExtractPrintableText;
import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.ResourcesLoader;
import com.anandhuarjunan.workspacetool.MainApp;
import com.anandhuarjunan.workspacetool.Util;
import com.anandhuarjunan.workspacetool.constants.Constants;
import com.anandhuarjunan.workspacetool.constants.ReloadableViews;
import com.anandhuarjunan.workspacetool.controller.choosedir.ChooseDirectoryController;
import com.anandhuarjunan.workspacetool.filescanner.EclipseWorkspaceDetector;
import com.anandhuarjunan.workspacetool.persistance.models.KvStrSettings;
import com.anandhuarjunan.workspacetool.persistance.models.Workspaces;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.control.ToggleGroup;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.Collation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoader;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoaderBean;
import static com.anandhuarjunan.workspacetool.ResourcesLoader.loadURL;

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
		initializeLoader();


	}

	private void initializeLoader() {
		ToggleButton chooseDir = createToggle("mfx-search", "Choose Root Directory");
		ToggleButton workspace = createToggle("mfx-circle-dot", "Workspaces");

		ToggleButton ide = createToggle("mfx-circle-dot", "IDE");
		ToggleButton jdk = createToggle("mfx-circle-dot", "JDK Manager");

		navBar.getChildren().add(chooseDir);
		navBar.getChildren().add(workspace);

		navBar.getChildren().add(ide);
		navBar.getChildren().add(jdk);

		try {
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

		} catch (IOException e) {
			e.printStackTrace();
		}



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
