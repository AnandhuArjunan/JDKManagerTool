package com.anandhuarjunan.workspacetool.views;

import java.io.IOException;

import com.anandhuarjunan.workspacetool.util.Util;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public interface NoDataPresenter {


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
