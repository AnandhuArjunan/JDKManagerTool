package com.anandhuarjunan.workspacetool.controller.jdk;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import com.anandhuarjunan.workspacetool.util.JavaEnvUtils;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class JdkController implements Initializable,ReloadableController,NoDataController {

	private final ToggleGroup toggleGroup;

	 @FXML
	    private MFXScrollPane contentPane;

	    @FXML
	    private ToggleButton download;

	    @FXML
	    private FlowPane ides;

	    @FXML
	    private ToggleButton installedJdk;

	    @FXML
	    protected Label javaVersion;

	    @FXML
	    protected Label javacVersion;

	    @FXML
	    private BorderPane mainPane;

	    @FXML
	    private ToggleButton settings;

	    @FXML
	    protected Label vendor;

		protected ExecutorService executorService = Executors.newSingleThreadExecutor();

	   public JdkController() {
		   this.toggleGroup = new ToggleGroup();
		   ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
	   }


	@Override
	public void reload() {
		loadJdk();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		setControls();

		loadJdk();
		try {
			setJavaHomeDetails();
		} catch (Exception e) {
			e.printStackTrace();
		}




	}

	private void setControls() {
		download.setToggleGroup(toggleGroup);
		settings.setToggleGroup(toggleGroup);
		installedJdk.setToggleGroup(toggleGroup);

	}


	protected void setJavaHomeDetails() throws Exception {

		Map<String,String> javaDetails = JavaEnvUtils.getJavaEnvDetails();
		javaVersion.setText(javaDetails.get(JavaEnvUtils.JRE_VERSION).trim());
		javacVersion.setText(javaDetails.get(JavaEnvUtils.JDK_VERSION).trim());
		vendor.setText(javaDetails.get(JavaEnvUtils.VENDOR).trim());




	}

	public void loadJdk() {
		ides.getChildren().clear();
		SessionFactory session =  HibernateUtils.getSessionFactory();
		 Session session2 = session.openSession();
		 CriteriaBuilder cb = session2.getCriteriaBuilder();
		    CriteriaQuery<JavaEnv> cq = cb.createQuery(JavaEnv.class);
		    Root<JavaEnv> rootEntry = cq.from(JavaEnv.class);
		    CriteriaQuery<JavaEnv> all = cq.select(rootEntry);

		    TypedQuery<JavaEnv> allQuery = session2.createQuery(all);
		    List<JavaEnv> workspacesResult =  allQuery.getResultList();
		    workspacesResult.forEach(w->{
			  FXMLLoader loader = new FXMLLoader(ResourcesLoader.loadURL("fxml/jdk/JdkBlock.fxml"));
				loader.setControllerFactory(c -> new JdkBlock(w,this));
				try {
					Parent root = loader.load();
					ides.getChildren().add(root);

				} catch (IOException e) {
					e.printStackTrace();
				}
		  });

		 addNoDataController(ides, mainPane,contentPane);
		 session2.close();

	}


}
