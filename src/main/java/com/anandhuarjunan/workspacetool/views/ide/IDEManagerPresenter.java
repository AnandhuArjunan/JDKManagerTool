package com.anandhuarjunan.workspacetool.views.ide;

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
import com.anandhuarjunan.workspacetool.persistance.models.WindowsIDE;
import com.anandhuarjunan.workspacetool.views.NoDataPresenter;
import com.anandhuarjunan.workspacetool.views.Reloadable;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class IDEManagerPresenter implements Initializable,Reloadable,NoDataPresenter {


    @FXML
    private FlowPane ides;

    @FXML
    private BorderPane mainPane;

    @FXML
    private MFXScrollPane contentPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadIde();
	}

	public void loadIde() {
		ides.getChildren().clear();
		SessionFactory session =  HibernateUtils.getSessionFactory();
		   Session session2 = session.openSession();
		 Transaction tx = session2.beginTransaction();

		    CriteriaBuilder cb = session2.getCriteriaBuilder();
		    CriteriaQuery<WindowsIDE> cq = cb.createQuery(WindowsIDE.class);
		    Root<WindowsIDE> rootEntry = cq.from(WindowsIDE.class);
		    CriteriaQuery<WindowsIDE> all = cq.select(rootEntry);

		    TypedQuery<WindowsIDE> allQuery = session2.createQuery(all);
		    List<WindowsIDE> workspacesResult =  allQuery.getResultList();
		    workspacesResult.forEach(w->{
			  FXMLLoader loader = new FXMLLoader(ResourcesLoader.loadURL("fxml/common/CommonNameLocBlock.fxml"));
				loader.setControllerFactory(c -> new IdeBlockPresenter(w));
				try {
					Parent root = loader.load();
					ides.getChildren().add(root);

				} catch (IOException e) {
					e.printStackTrace();
				}
		  });
			 addNoDataController(ides, mainPane, contentPane);


		 tx.commit();
		 session2.close();

	}

	@Override
	public void reload() {

		loadIde();
	}
}
