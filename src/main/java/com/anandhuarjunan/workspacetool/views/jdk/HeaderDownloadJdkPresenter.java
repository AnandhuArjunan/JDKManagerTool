package com.anandhuarjunan.workspacetool.views.jdk;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.inject.Inject;

import com.anandhuarjunan.workspacetool.services.downloadjava.JavaReleaseMetadataDownloader;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class HeaderDownloadJdkPresenter implements Initializable {

    @FXML
    private ComboBox<?> categoryDropDown;

    @FXML
    private MFXButton clearSearchBtn;

    @FXML
    private Label downloadStatus;

    @FXML
    private Label metadataUrl;

    @FXML
    private TextField searchBox;

    @FXML
    private MFXButton searchBtn;

    @FXML
    private Button syncmetadatabtn;
    @FXML
    private MFXProgressBar progressbar;

    @FXML
    private VBox metadataInfo;

    @Inject
	ExecutorService executorService = null;

    @Inject
    private JavaReleaseMetadataDownloader javaReleaseMetadataDownloader;

    @Inject
    private Consumer<File> metaDataFile;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		disableProgressBar();
		metadataUrl.setText(JavaReleaseMetadataDownloader.METADATAURL);
		syncmetadatabtn.setOnAction(ev->syncMetadata());
	}

	public void syncMetadata() {
		enableProgressBar();
		downloadStatus.setText("Download In Progress!");
		CompletableFuture.supplyAsync(fileSupplier).thenAcceptAsync(afterFetch, Platform::runLater);
	}

	Supplier<File> fileSupplier = ()->{
		try {
			return javaReleaseMetadataDownloader.downloadMetadata();
		} catch (IOException e) {
			return null;
		}
	};

	Consumer<File> afterFetch = (f)->{
		if(Objects.nonNull(f)) {
			metaDataFile.accept(f);
			downloadStatus.setText("Successfully Updated..");
		}else {
			downloadStatus.setText("Failed to fetch !Try again");
		}
		 disableProgressBar();
	};


	private void disableProgressBar() {
		metadataInfo.getChildren().remove(progressbar);
	}

	private void enableProgressBar() {
		int size = metadataInfo.getChildren().size();
		if(size == 3) {
			metadataInfo.getChildren().add(progressbar);

		}else if(size == 4){
			metadataInfo.getChildren().set(3,progressbar);

		}
	}
}

