package com.anandhuarjunan.workspacetool.views.jdk;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;
import com.anandhuarjunan.workspacetool.persistance.service.GenericServiceImpl;
import com.anandhuarjunan.workspacetool.util.Action;
import com.anandhuarjunan.workspacetool.views.NoDataPresenter;
import com.anandhuarjunan.workspacetool.views.Reloadable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class InstalledJdkPresenter implements Initializable,Reloadable,NoDataPresenter {

	@FXML
    private FlowPane ides;
	 @FXML
	    private BorderPane pane;

	 @Inject
	 private GenericServiceImpl<JavaEnv, Integer> javaEnvService;

	 private HeaderInstalledJdkView headerInstalledJdkView;


	@Override
	public void initialize(URL location, ResourceBundle resources) {


		Map<String, Object> customProperties2 = new HashMap<>();
		customProperties2.put("clearAction", onClearAction);
		customProperties2.put("searchDataConsumer", onSearchAction);
	    headerInstalledJdkView = new HeaderInstalledJdkView(customProperties2::get);
		loadJdk();


	}

	Action onClearAction = ()->{
		loadJdk();
	};

	BiConsumer<String,String> onSearchAction = (search,category)->{
		
		if(!StringUtils.isEmpty(search)) {
			if("Version".equalsIgnoreCase(category)) {
				ides.getChildren().setAll(ides.getChildren().stream().filter(e->((JavaEnv)e.getUserData()).getVersion().toLowerCase().contains(search)).collect(Collectors.toList()));
			}else if("Vendor".equalsIgnoreCase(category)){
				ides.getChildren().setAll(ides.getChildren().stream().filter(e->((JavaEnv)e.getUserData()).getCompany().toLowerCase().contains(search)).collect(Collectors.toList()));

			}
		}
	};

	HeaderInstalledJdkView getHeaderView() {
		return headerInstalledJdkView;
	}

	@Override
	public void reload() {
		loadJdk();

	}

	public void loadJdk()  {
		ides.getChildren().clear();
		List<JavaEnv> javaenv =  javaEnvService.findAll(JavaEnv.class);
		javaenv.forEach(w->{
				Map<String, Object> customProperties = new HashMap<>();
				customProperties.put("javaEnvModel", w);
				HeaderInstalledJdkPresenter headerInstalledJdkPresenter = (HeaderInstalledJdkPresenter) headerInstalledJdkView.getPresenter();
				Action afterSetDefaultAction = ()->{
					reload();
					headerInstalledJdkPresenter.javaVersion.setText(w.getVersion());
					headerInstalledJdkPresenter.vendor.setText(w.getCompany().trim());
				};
				customProperties.put("afterSetDefaultAction", afterSetDefaultAction);
		    	JdkInstalledBlockView jdkInstalledBlock = new JdkInstalledBlockView(customProperties::get);
		    	Parent parent = jdkInstalledBlock.getView();
		    	parent.setUserData(w);
		    	if(!((JdkInstalledBlockPresenter)jdkInstalledBlock.getPresenter()).isDefault()) {
		    		ides.getChildren().add(parent);
		    	}else {
		    		ides.getChildren().add(0,parent);
		    	}
		  });


	}


}
