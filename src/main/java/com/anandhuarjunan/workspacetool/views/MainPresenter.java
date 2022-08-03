package com.anandhuarjunan.workspacetool.views;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import com.airhacks.afterburner.injection.Injector;
import com.anandhuarjunan.workspacetool.constants.Constants;
import com.anandhuarjunan.workspacetool.constants.ReloadableViews;
import com.anandhuarjunan.workspacetool.persistance.models.KvStrSettings;
import com.anandhuarjunan.workspacetool.util.Action;
import com.anandhuarjunan.workspacetool.util.AnimationUtils;
import com.anandhuarjunan.workspacetool.util.SimpleViewSwitcher;
import com.anandhuarjunan.workspacetool.util.Util;
import com.anandhuarjunan.workspacetool.util.ViewSwitcherBean;
import com.anandhuarjunan.workspacetool.views.choosedir.ChooseDirectoryPresenter;
import com.anandhuarjunan.workspacetool.views.choosedir.ChooseDirectoryView;
import com.anandhuarjunan.workspacetool.views.jdk.JdkManagerView;

import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoader;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoaderBean;
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

public class MainPresenter implements Initializable {

	private double xOffset;

	private double yOffset;


	private ToggleGroup toggleGroup;

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

	@Inject
	private Stage stage;

    @FXML
    private Label username;

    @FXML
    private BorderPane root;

	@Inject
	private ChooseDirectoryView chooseDirectoryView = null;

	@Inject
	private JdkManagerView jdkManagerView = null;

	@Inject
	private LoadingView loadingView = null;



	public MainPresenter() {
		toggleGroup = new ToggleGroup();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
		addWindowDragAndCloseEvents();
		username.setText(System.getProperty("user.name"));
		ScrollUtils.addSmoothScrolling(scrollPane);
		loadViews();

	}



	private void addWindowDragAndCloseEvents() {
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

	}

	private void loadViews()  {

		SimpleViewSwitcher simpleViewSwitcher = new SimpleViewSwitcher();
		simpleViewSwitcher.addView(ViewSwitcherBean.builder().ofDefault(true).ofActionButton(createToggle("mfx-search", "Choose Root Directory")).ofView(chooseDirectoryView).get());
		simpleViewSwitcher.addView(ViewSwitcherBean.builder().ofActionButton(createToggle("mfx-circle-dot", "JDK Manager")).ofView(jdkManagerView).get());

		simpleViewSwitcher.addContentPane(contentPane);
		simpleViewSwitcher.addToggleBar(navBar);
		simpleViewSwitcher.addLoadingView(loadingView);
		simpleViewSwitcher.show();

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
