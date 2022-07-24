package com.anandhuarjunan.workspacetool.controller.jdk;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.ResourcesLoader;
import com.anandhuarjunan.workspacetool.controller.NoDataController;
import com.anandhuarjunan.workspacetool.controller.ReloadableController;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class JdkController implements Initializable,ReloadableController,NoDataController {



    @FXML
    private FlowPane ides;


    @FXML
    private BorderPane mainPane;

    @FXML
    private MFXScrollPane contentPane;

	@Override
	public void reload() {
		loadJdk();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadJdk();

	}

	public void loadJdk() {
		ides.getChildren().clear();
		SessionFactory session =  HibernateUtils.getSessionFactory();
		 Session session2 = session.openSession();
		 Transaction tx = session2.beginTransaction();

		 CriteriaBuilder cb = session2.getCriteriaBuilder();
		    CriteriaQuery<JavaEnv> cq = cb.createQuery(JavaEnv.class);
		    Root<JavaEnv> rootEntry = cq.from(JavaEnv.class);
		    CriteriaQuery<JavaEnv> all = cq.select(rootEntry);

		    TypedQuery<JavaEnv> allQuery = session2.createQuery(all);
		    List<JavaEnv> workspacesResult =  allQuery.getResultList();
		    workspacesResult.forEach(w->{
			  FXMLLoader loader = new FXMLLoader(ResourcesLoader.loadURL("fxml/common/CommonNameLocBlock.fxml"));
				loader.setControllerFactory(c -> new JdkBlock(w));
				try {
					Parent root = loader.load();
					ides.getChildren().add(root);

				} catch (IOException e) {
					e.printStackTrace();
				}
		  });

		 addNoDataController(ides, mainPane,contentPane);
		 tx.commit();
		 session2.close();


	}


}
