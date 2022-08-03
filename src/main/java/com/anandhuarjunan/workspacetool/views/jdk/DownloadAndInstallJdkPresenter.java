package com.anandhuarjunan.workspacetool.views.jdk;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.ResourcesLoader;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;
import com.anandhuarjunan.workspacetool.util.Action;
import com.anandhuarjunan.workspacetool.views.NoDataPresenter;
import com.anandhuarjunan.workspacetool.views.Reloadable;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
public class DownloadAndInstallJdkPresenter implements Initializable,Reloadable,NoDataPresenter {


	private HeaderDownloadJdkView headerDownloadJdkView;

	public DownloadAndInstallJdkPresenter() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Map<String, Object> customProperties = new HashMap<>();
		Consumer<File> metaDataFile = this::afterGettingMetadata;
	    customProperties.put("metaDataFile", metaDataFile);
	    customProperties.put("clearAction", onClearAction);
	    customProperties.put("searchDataConsumer", onSearchAction);
	    headerDownloadJdkView = new HeaderDownloadJdkView(customProperties::get);
	}

	Action onClearAction = ()->{

	};

	BiConsumer<String,String> onSearchAction = (search,category)->{

	};

	private void afterGettingMetadata(File metadataFile) {
	}

	public HeaderDownloadJdkView getHeaderView() {
		return headerDownloadJdkView;
	}

	@Override
	public void reload() {


	}

}
