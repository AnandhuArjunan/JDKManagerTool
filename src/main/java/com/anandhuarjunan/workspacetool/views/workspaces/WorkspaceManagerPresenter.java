package com.anandhuarjunan.workspacetool.views.workspaces;

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
import com.anandhuarjunan.workspacetool.persistance.models.Workspaces;
import com.anandhuarjunan.workspacetool.views.NoDataPresenter;
import com.anandhuarjunan.workspacetool.views.Reloadable;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class WorkspaceManagerPresenter implements Initializable,Reloadable,NoDataPresenter {


    @FXML
    private FlowPane workspaces;

    @FXML
    private BorderPane mainPane;

    @FXML
    private MFXScrollPane contentPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		loadWorkspaces();

		}


	public void loadWorkspaces() {
		workspaces.getChildren().clear();
		SessionFactory session =  HibernateUtils.getSessionFactory();
		 Session session2 = session.openSession();
		 Transaction tx = session2.beginTransaction();

		 CriteriaBuilder cb = session2.getCriteriaBuilder();
		    CriteriaQuery<Workspaces> cq = cb.createQuery(Workspaces.class);
		    Root<Workspaces> rootEntry = cq.from(Workspaces.class);
		    CriteriaQuery<Workspaces> all = cq.select(rootEntry);

		    TypedQuery<Workspaces> allQuery = session2.createQuery(all);
		    List<Workspaces> workspacesResult =  allQuery.getResultList();

		    workspacesResult.forEach(w->{
			  FXMLLoader loader = new FXMLLoader(ResourcesLoader.loadURL("fxml/common/CommonNameLocBlock.fxml"));
				loader.setControllerFactory(c -> new WorkspaceBlockPresenter(w));
				try {
					Parent root = loader.load();
					workspaces.getChildren().add(root);

				} catch (IOException e) {
					e.printStackTrace();
				}
		  });

	     addNoDataController(workspaces, mainPane, contentPane);

		 tx.commit();
		 session2.close();
	}

	@Override
	public void reload() {

		loadWorkspaces();
	}

}