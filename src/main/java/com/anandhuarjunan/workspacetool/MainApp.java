package com.anandhuarjunan.workspacetool;

import com.anandhuarjunan.workspacetool.controller.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader(ResourcesLoader.loadURL("fxml/Main.fxml"));
    	MainController mainController = new MainController(stage);
		loader.setControllerFactory(c -> mainController);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setAlwaysOnTop(true);
		stage.setOnHidden(mainController::shutDown);
		stage.setTitle("Workspace Manager");
		stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
