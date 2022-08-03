package com.anandhuarjunan.workspacetool.views.jdk;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.airhacks.afterburner.views.FXMLView;
import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.ResourcesLoader;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;
import com.anandhuarjunan.workspacetool.persistance.service.GenericService;
import com.anandhuarjunan.workspacetool.persistance.service.GenericServiceImpl;
import com.anandhuarjunan.workspacetool.util.Action;
import com.anandhuarjunan.workspacetool.util.Util;
import com.anandhuarjunan.workspacetool.views.NoDataPresenter;
import com.anandhuarjunan.workspacetool.views.Reloadable;

import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

public class InstalledJdkPresenter implements Initializable,Reloadable,NoDataPresenter {

	 @FXML
	 protected FlowPane ides;

	 protected List<Node> allLocalJdkList = null;


	 @Inject
	 private GenericServiceImpl<JavaEnv, Integer> javaEnvService;

	 private HeaderInstalledJdkView headerInstalledJdkView;

	 public InstalledJdkPresenter() {
		allLocalJdkList = new ArrayList<>();
	 }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Map<String, Object> customProperties = new HashMap<>();
	    customProperties.put("clearAction", onClearAction);
	    customProperties.put("searchDataConsumer", onSearchAction);
	    headerInstalledJdkView = new HeaderInstalledJdkView(customProperties::get);
		loadJdk();

	}

	Action onClearAction = ()->{
		ides.getChildren().clear();
		ides.getChildren().addAll(allLocalJdkList);
	};

	BiConsumer<String,String> onSearchAction = (search,category)->{
		if(!StringUtils.isEmpty(search)) {
			filterJdks(search,category);
		}
	};

	public HeaderInstalledJdkView getHeaderView() {
		return headerInstalledJdkView;
	}

	@Override
	public void reload() {
		loadJdk();

	}

	public void loadJdk()  {
		ides.getChildren().clear();
		allLocalJdkList.clear();
		List<JavaEnv> javaenv =  javaEnvService.findAll(JavaEnv.class);
		javaenv.forEach(w->{
				Map<String, Object> customProperties = new HashMap<>();
				customProperties.put("javaEnvModel", w);
				HeaderInstalledJdkPresenter headerInstalledJdkPresenter = (HeaderInstalledJdkPresenter) headerInstalledJdkView.getPresenter();
				Action afterSetDefaultAction = ()->{
					reload();
					headerInstalledJdkPresenter.javacVersion.setText(w.getVersion());
					headerInstalledJdkPresenter.javaVersion.setText(w.getVersion());
					headerInstalledJdkPresenter.vendor.setText(w.getCompany().trim());
				};
				customProperties.put("afterSetDefaultAction", afterSetDefaultAction);
		    	JdkInstalledBlockView jdkInstalledBlock = new JdkInstalledBlockView(customProperties::get);
		    	jdkInstalledBlock.getView().setUserData(w);
		    	if(!((JdkInstalledBlockPresenter)jdkInstalledBlock.getPresenter()).isDefault()) {
		    		ides.getChildren().add(jdkInstalledBlock.getView());
		    	}else {
		    		ides.getChildren().add(0,jdkInstalledBlock.getView());
		    	}
		  });
	   allLocalJdkList.addAll(ides.getChildren());//for search operation
		 //addNoDataController(ides, controller.mainPane,controller.contentPane);

	}

	public void filterJdks(String searchWord,String category) {
		Predicate<Node> predicate = null;
        if("Vendor".equalsIgnoreCase(category)) {
        	predicate=  e->((JavaEnv)e.getUserData()).getCompany().toLowerCase().contains(searchWord.toLowerCase());
        }else if("Version".equalsIgnoreCase(category)) {
        	predicate = e->((JavaEnv)e.getUserData()).getVersion().toLowerCase().contains(searchWord.toLowerCase());
        }
        List<Node> filtered = ides.getChildren().parallelStream().filter(predicate).collect(Collectors.toList());
        ides.getChildren().clear();
        ides.getChildren().addAll(filtered);
	}
}
