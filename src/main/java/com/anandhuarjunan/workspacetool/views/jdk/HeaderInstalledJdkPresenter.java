package com.anandhuarjunan.workspacetool.views.jdk;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.anandhuarjunan.workspacetool.util.Action;
import com.anandhuarjunan.workspacetool.util.JavaEnvUtils;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HeaderInstalledJdkPresenter implements Initializable {
    @FXML
    private ComboBox<String> categoryDropDown;

    @FXML
    private MFXButton clearSearchBtn;

    @FXML
    protected Label javaVersion;

    @FXML
    protected Label javacVersion;

    @FXML
    private TextField searchBox;

    @FXML
    private MFXButton searchBtn;

    @FXML
    protected Label vendor;

    @Inject
    private Action clearAction;

    @Inject
    private BiConsumer<String,String> searchDataConsumer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			setJavaHomeDetails();
			fillSearchCategories();
			clearSearchBtn.setOnAction(ev->onSearchClearAction());
			searchBtn.setOnAction(ev->onSearchAction());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private void onSearchClearAction() {
		clearAction.action();
	}
	private void onSearchAction() {
			onSearchClearAction();
			if(!StringUtils.isEmpty(searchBox.getText())) {
				searchDataConsumer.accept(searchBox.getText(), categoryDropDown.getSelectionModel().getSelectedItem());
			}
	}
	private void fillSearchCategories() {
		categoryDropDown.getItems().addAll("Version","Vendor");
		categoryDropDown.getSelectionModel().selectFirst();
	}

	protected void setJavaHomeDetails() throws Exception {
		Map<String,String> javaDetails = JavaEnvUtils.getJavaEnvDetails();
		javaVersion.setText(javaDetails.get(JavaEnvUtils.JRE_VERSION).trim());
		javacVersion.setText(javaDetails.get(JavaEnvUtils.JDK_VERSION).trim());
		vendor.setText(javaDetails.get(JavaEnvUtils.VENDOR).trim());
	}
}
