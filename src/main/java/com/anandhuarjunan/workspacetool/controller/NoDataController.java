package com.anandhuarjunan.workspacetool.controller;

import java.io.IOException;

import com.anandhuarjunan.workspacetool.Util;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public interface NoDataController {


	default void addNoDataController(FlowPane flowPane,BorderPane mainPane, ScrollPane container) {
		if(flowPane.getChildren().isEmpty()) {
			try {
				mainPane.setCenter(Util.loadFxml("fxml/common/NotFound.fxml"));
			} catch (IOException e) {

			}
		}else {
			mainPane.setCenter(container);
		}
	}



}
