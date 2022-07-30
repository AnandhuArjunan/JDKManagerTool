package com.anandhuarjunan.workspacetool.controller.common;

import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.commons.io.FilenameUtils;

import com.anandhuarjunan.workspacetool.ResourcesLoader;

import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class NormalNameLocBlockController implements Initializable {
	@FXML
	protected ImageView icon;

    @FXML
	protected Label name;

    @FXML
    protected MFXFontIcon openInExp;

    @FXML
    protected Label wlocation;

    @FXML
    private VBox box;

    @FXML
    private BorderPane mainPane;

    private String strname;
    private String flocation;
    private  Blob imgIcon;

    public NormalNameLocBlockController(String name,String location,Blob icon) {
    	this.strname = name;
    	this.flocation = location;
    	this.imgIcon = icon;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		openInExp.setOnMouseClicked(this::openInExplorer);

		name.setText(strname);
		wlocation.setText(flocation);
		if(null != imgIcon) {
			try {

				icon.setImage(new Image(imgIcon.getBinaryStream()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}

	private void openInExplorer(Event event) {
		 try {
			Runtime.getRuntime().exec("explorer "+FilenameUtils.separatorsToWindows(wlocation.getText()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void addToView(Node node) {
		box.getChildren().add(node);
	}

	public void addStyleSheet(String path) {
		mainPane.getStylesheets().clear();
		mainPane.getStylesheets().add(ResourcesLoader.load(path));
	}

	public void changeStyle(String styleClass) {
		mainPane.getStyleClass().clear();
		mainPane.getStyleClass().add(styleClass);
	}

}
