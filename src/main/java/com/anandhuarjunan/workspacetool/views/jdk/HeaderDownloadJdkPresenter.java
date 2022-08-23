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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.anandhuarjunan.workspacetool.constants.Constants;
import com.anandhuarjunan.workspacetool.services.DownloaderService;
import com.anandhuarjunan.workspacetool.util.Util;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

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

    @FXML
    private Label downloadedByte;

    @FXML
    private HBox progressBlock;

    @Inject
	ExecutorService executorService = null;

    @Inject
    private DownloaderService downloaderService;

    @Inject
    private Consumer<File> metaDataFile;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(Util.checkIfMetadaFileAlreadyExixts()) {
			syncmetadatabtn.setText("Update Metada");
		}else {
			syncMetadata();
			syncmetadatabtn.setText("Sync Metadata");
		}
		disableProgressBar();
		metadataUrl.setText(Constants.METADATAURL);
		syncmetadatabtn.setOnAction(ev->syncMetadata());
	}



	public void syncMetadata() {
		enableProgressBar();
		downloadStatus.setText("Download In Progress!");
		CompletableFuture.supplyAsync(fileSupplier,executorService).thenAcceptAsync(afterFetch, Platform::runLater);
	}


	Consumer<Pair<Long,Long>> fileProgressConsumer = pair->{
		Platform.runLater(()->{
			progressbar.setProgress(pair.getKey()/(double)pair.getValue());
			downloadedByte.setText(FileUtils.byteCountToDisplaySize(pair.getKey())+"/"+FileUtils.byteCountToDisplaySize(pair.getValue()));
		});
	};

	Supplier<File> fileSupplier = ()->{
		try {
			return downloaderService.downloadAndUpdateProgress(Constants.METADATAURL, new File(Constants.METADTA_DOWNLOAD_LOC),fileProgressConsumer );
		} catch (IOException e) {
			Platform.runLater(()->{
				downloadStatus.setText("Failed to fetch !Try again");
			});

			return null;
		}
	};

	Consumer<File> afterFetch = (f)->{
		if(Objects.nonNull(f)) {
			metaDataFile.accept(f);
			downloadStatus.setText("Successfully Updated..");
		}
		 disableProgressBar();
	};


	private void disableProgressBar() {
		metadataInfo.getChildren().remove(progressBlock);

	}

	private void enableProgressBar() {
		progressbar.setProgress(-1);
		downloadedByte.setText("");
		int size = metadataInfo.getChildren().size();
		if(size == 3) {
			metadataInfo.getChildren().add(progressBlock);

		}else if(size == 4){
			metadataInfo.getChildren().set(3,progressBlock);

		}
	}
}

