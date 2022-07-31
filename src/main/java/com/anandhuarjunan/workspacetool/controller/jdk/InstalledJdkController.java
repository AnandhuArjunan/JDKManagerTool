package com.anandhuarjunan.workspacetool.controller.jdk;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.ResourcesLoader;
import com.anandhuarjunan.workspacetool.controller.NoDataController;
import com.anandhuarjunan.workspacetool.controller.ReloadableController;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;
import com.anandhuarjunan.workspacetool.util.Util;

import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

public class InstalledJdkController implements Initializable,ReloadableController,NoDataController {

	 @FXML
	 protected FlowPane ides;

	 protected List<Node> allLocalJdkList = null;

	 JdkController controller = null;



	public InstalledJdkController(JdkController controller) {
		allLocalJdkList = new ArrayList<>();
		this.controller = controller;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadJdk();

	}

	@Override
	public void reload() {
		loadJdk();

	}

	public void loadJdk() {
		ides.getChildren().clear();
		allLocalJdkList.clear();
		SessionFactory session =  HibernateUtils.getSessionFactory();
		 Session session2 = session.openSession();
		 CriteriaBuilder cb = session2.getCriteriaBuilder();
		    CriteriaQuery<JavaEnv> cq = cb.createQuery(JavaEnv.class);
		    Root<JavaEnv> rootEntry = cq.from(JavaEnv.class);
		    CriteriaQuery<JavaEnv> all = cq.select(rootEntry);

		    TypedQuery<JavaEnv> allQuery = session2.createQuery(all);
		    List<JavaEnv> workspacesResult =  allQuery.getResultList();
		    workspacesResult.forEach(w->{
		    	JdkInstalledBlock jdkInstalledBlock = new JdkInstalledBlock(w,controller);
		    	try {
		    	Parent node = Util.loadFxml("fxml/jdk/JdkBlock.fxml",jdkInstalledBlock);
		    	node.setUserData(w);
		    	if(!jdkInstalledBlock.isDefault()) {
					ides.getChildren().add(node);

		    	}else {
		    		ides.getChildren().add(0,node);

		    	}
				} catch (IOException e) {
					e.printStackTrace();
				}
		  });
	    allLocalJdkList.addAll(ides.getChildren());
		 addNoDataController(ides, controller.mainPane,controller.contentPane);
		 session2.close();

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
