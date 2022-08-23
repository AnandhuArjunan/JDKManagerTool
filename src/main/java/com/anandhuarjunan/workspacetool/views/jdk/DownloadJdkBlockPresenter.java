package com.anandhuarjunan.workspacetool.views.jdk;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.anandhuarjunan.workspacetool.services.model.JavaReleaseMetadata;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DownloadJdkBlockPresenter implements Initializable {

    @FXML
    private Label architecture;

    @FXML
    private VBox box;

    @FXML
    private VBox buttonGroup;

    @FXML
    private HBox currentJavaHomeFlag;

    @FXML
    private Label currentStatus;

    @FXML
    private MFXButton delete;

    @FXML
    private ImageView icon;

    @FXML
    private Label imageType;

    @FXML
    private Label jvmImpl;

    @FXML
    private MFXButton locateInExplorer;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label name;

    @FXML
    private MFXProgressBar progressBar;

    @FXML
    private Label releaseType;

    @FXML
    private MFXButton setdefault;

    @FXML
    private Label size;

    @FXML
    private MFXFontIcon tickMark;

    @FXML
    private Label vendor;

    @FXML
    private Label wlocation;

	    @Inject
	    private JavaReleaseMetadata javaReleaseMetadata;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(Objects.nonNull(javaReleaseMetadata)) {
			name.setText("Java "+javaReleaseMetadata.getJavaVersion());
			vendor.setText(javaReleaseMetadata.getVendor());
			architecture.setText(javaReleaseMetadata.getArchitecture());
			releaseType.setText(javaReleaseMetadata.getReleaseType());
			jvmImpl.setText(javaReleaseMetadata.getJvmImpl());
			imageType.setText(javaReleaseMetadata.getImageType());
			size.setText(String.valueOf(javaReleaseMetadata.getSize()));
		}

	}

}
