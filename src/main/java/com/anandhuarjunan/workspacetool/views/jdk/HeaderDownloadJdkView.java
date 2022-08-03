package com.anandhuarjunan.workspacetool.views.jdk;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import com.airhacks.afterburner.views.FXMLView;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HeaderDownloadJdkView extends FXMLView {

	public HeaderDownloadJdkView(Function<String, Object> injectionContext) {
        super(injectionContext);
    }
}

