package com.anandhuarjunan.workspacetool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.airhacks.afterburner.injection.Injector;
import com.anandhuarjunan.workspacetool.views.MainView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApp extends Application {

	ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void start(Stage stage) throws Exception {
    	System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        Map<Object, Object> customProperties = new HashMap<>();
        customProperties.put("stage", stage);
        customProperties.put("executorService", executorService);

        Injector.setConfigurationSource(customProperties::get);
        MainView mainView = new MainView();
		Scene scene = new Scene(mainView.getView());
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		//stage.setAlwaysOnTop(true);
		stage.show();




    }

    @Override
    public void stop() throws Exception {
    	executorService.shutdownNow();
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
