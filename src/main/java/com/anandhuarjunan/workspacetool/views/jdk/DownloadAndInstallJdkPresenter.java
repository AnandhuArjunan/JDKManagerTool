package com.anandhuarjunan.workspacetool.views.jdk;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.ResourcesLoader;
import com.anandhuarjunan.workspacetool.constants.Constants;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;
import com.anandhuarjunan.workspacetool.services.model.JavaReleaseMetadata;
import com.anandhuarjunan.workspacetool.util.Action;
import com.anandhuarjunan.workspacetool.util.Util;
import com.anandhuarjunan.workspacetool.views.NoDataPresenter;
import com.anandhuarjunan.workspacetool.views.Reloadable;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.palexdev.materialfx.controls.MFXPagination;
import io.github.palexdev.materialfx.controls.cell.MFXPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.FlowPane;
public class DownloadAndInstallJdkPresenter implements Initializable,Reloadable,NoDataPresenter {


	private HeaderDownloadJdkView headerDownloadJdkView;

	@FXML
	private FlowPane ides;

    @FXML
    private Label downloadLoc;

    @FXML
    private Pagination paginator;

    private Map<Integer,List<DownloadJdkBlockView>> paginatedBlocks = null;


	public DownloadAndInstallJdkPresenter() {
		paginatedBlocks = new HashMap<>();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(Util.checkIfMetadaFileAlreadyExixts()) {
			afterGettingMetadata(Util.getMetadaFile());
		}

		downloadLoc.setText(Constants.RELEASE_DOWNLOAD_LOC);
		Map<String, Object> customProperties = new HashMap<>();
		Consumer<File> metaDataFile = this::afterGettingMetadata;
	    customProperties.put("metaDataFile", metaDataFile);
	    customProperties.put("clearAction", onClearAction);
	    customProperties.put("searchDataConsumer", onSearchAction);
	    headerDownloadJdkView = new HeaderDownloadJdkView(customProperties::get);

	    paginator.setPageFactory((INDEX)->{
	    	ides.setVisible(true);
	    	ides.getChildren().setAll(paginatedBlocks.get(INDEX));
	    	return ides;
	    });

	}

	Action onClearAction = ()->{

	};

	BiConsumer<String,String> onSearchAction = (search,category)->{

	};

	private void afterGettingMetadata(File metadataFile) {
		ides.getChildren().clear();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JavaReleaseMetadata[] javaReleaseMetadata = mapper.readValue(metadataFile,JavaReleaseMetadata[].class);
			prepareView(javaReleaseMetadata);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private void prepareView(JavaReleaseMetadata[] javaReleaseMetadatas) {

    	 MutableInt i = new MutableInt(0);
    	List<JavaReleaseMetadata> javaReleaseMetadatasList = Arrays.asList(javaReleaseMetadatas);
    	List<List<JavaReleaseMetadata>> subLists = ListUtils.partition(javaReleaseMetadatasList, 4);
    	subLists.forEach(javaList->{
    		List<DownloadJdkBlockView> blockViews = new ArrayList<>();
    		javaList.forEach(javaInfo ->blockViews.add(getReleaseBlock(javaInfo)));
    		paginatedBlocks.put(i.intValue(), blockViews);
    		i.increment() ;
    	});
    	paginator.setCurrentPageIndex(1);
    	paginator.setPageCount(i.intValue());

    	//ides.getChildren().setAll(paginatedBlocks.get(0));



	}

    private DownloadJdkBlockView getReleaseBlock(JavaReleaseMetadata releaseMetadata) {
    	Map<String, Object> customProperties = new HashMap<>();
	    customProperties.put("javaReleaseMetadata", releaseMetadata);
	    return new DownloadJdkBlockView(customProperties::get);
    }

	HeaderDownloadJdkView getHeaderView() {
		return headerDownloadJdkView;
	}

	@Override
	public void reload() {


	}

}
