package com.anandhuarjunan.workspacetool.views.choosedir;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.javatuples.Triplet;

import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.ResourcesLoader;
import com.anandhuarjunan.workspacetool.constants.Constants;
import com.anandhuarjunan.workspacetool.constants.ReloadableViews;
import com.anandhuarjunan.workspacetool.filemetadata.AbstractFileMetadata;
import com.anandhuarjunan.workspacetool.filemetadata.JavaJreMetadata;
import com.anandhuarjunan.workspacetool.filemetadata.WindowsAndroidStudioMetadata;
import com.anandhuarjunan.workspacetool.filemetadata.WindowsEclipseIDEMetadata;
import com.anandhuarjunan.workspacetool.filemetadata.WindowsEclipseWorkspaceMetadata;
import com.anandhuarjunan.workspacetool.filemetadata.WindowsStsIdeMetadata;
import com.anandhuarjunan.workspacetool.filescanner.AbstractFileDetector;
import com.anandhuarjunan.workspacetool.filescanner.jdk.windows.WindowsJreDetector;
import com.anandhuarjunan.workspacetool.util.Action;
import com.anandhuarjunan.workspacetool.util.AnimationUtils;
import com.anandhuarjunan.workspacetool.util.JFXUtils;
import com.anandhuarjunan.workspacetool.util.SimpleViewSwitcher;
import com.anandhuarjunan.workspacetool.util.Util;
import com.anandhuarjunan.workspacetool.views.Reloadable;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ChooseDirectoryPresenter implements Initializable {


	@FXML
	private MFXButton jdkBtn;

	@FXML
	private MFXTextField jdkLoc;

	@FXML
	private MFXButton jdkSync;

	@FXML
	private Label ongoingStatus;

	@FXML
	private VBox progressContainer;

	@FXML
	private MFXProgressBar progressBar;

	@FXML
	private HBox statusContainer;

	@FXML
	private Label scanning;

	@FXML
	private FlowPane resultFlow;

	@FXML
	private MFXScrollPane statusPane;

	@FXML
	private BorderPane mainWindow;

    @FXML
    private MFXFontIcon closeResult;

	private EnumMap<ReloadableViews, Object> dependencyControllers = null;

	@Inject
	private ExecutorService executorService;
	
	@Inject
	private SimpleViewSwitcher simpleViewSwitcher;

	public ChooseDirectoryPresenter() {
		dependencyControllers = new EnumMap<>(ReloadableViews.class);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("poi chaavu");

		initializeChooseDir();
		addDirectoryChoosers();
		hideProgessPane();
		addSyncButtonHandlers();
		mainWindow.setBottom(null);
		closeResult.setOnMouseClicked(ev->hideStatus());
	}

	private void hideStatus() {
		enableButtons();
		Action hideAction = ()->mainWindow.setBottom(null);
		AnimationUtils.addAnimating(hideAction, AnimationUtils.fadeOutAnimationSupplier(statusPane));
	}
	public void addDependencyController(ReloadableViews key,Object controller) {
		dependencyControllers.put(key, controller);
	}

	private void addSyncButtonHandlers() {


		jdkSync.setOnAction(ev -> startJdkSync());


	}

	private void enableButtons() {

		JFXUtils.enableNodes(jdkBtn,jdkSync);

	}



	private void startJdkSync() {

		try {
			SyncDirectoryBuilder directoryBuilder = new SyncDirectoryBuilder();
			directoryBuilder
					.ofTableName("JAVA_ENV")
					.haveToReload(ReloadableViews.JDK)
					.ofMetadata(new Triplet<>(jdkLoc.getText(),JavaJreMetadata.class, WindowsJreDetector.class))
					.startSync();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private void hideProgessPane() {
		progressContainer.getChildren().remove(progressBar);
		progressContainer.getChildren().remove(ongoingStatus);
		progressContainer.getChildren().remove(scanning);
	}

	private void addDirectoryChoosers() {
		addDirectoryChooser(jdkBtn);
	}

	private void addDirectoryChooser(Button clickAction) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		clickAction.setOnAction(e -> {
			File selectedDirectory = directoryChooser.showDialog(clickAction.getScene().getWindow());
			if (Objects.nonNull(selectedDirectory)) {
					String content = jdkLoc.getText();
					jdkLoc.setText(content+File.pathSeparator+selectedDirectory.getAbsolutePath());

					Util.putSettings(Constants.JDK_LOCATION, selectedDirectory.getAbsolutePath());
			
			}

		});
	}

	private void initializeChooseDir() {
		loadSettingsFromDb();
	}

	private void loadSettingsFromDb() {
		jdkLoc.setText(Util.getSettings(Constants.JDK_LOCATION));

	}

	public synchronized void onScanComplete(AbstractFileDetector abstractFileDetector, int fileFound) {

			Map<String, Object> customProperties = new HashMap<>();
		    customProperties.put("fileFound", String.valueOf(fileFound));
		    customProperties.put("viewSwitcher", simpleViewSwitcher);
			StatusBlockView blockView = new StatusBlockView(customProperties::get);
			resultFlow.getChildren().add(blockView.getView());
	}

	public class SyncDirectory {

		private Class<?> metadataClass = null;
		private Consumer<File> fileConsumer = null;
		private AbstractFileDetector abstractFileDetector = null;

		public SyncDirectory(String fileDirToScan,Class<? extends AbstractFileMetadata<?>> metadataClass,Class<? extends AbstractFileDetector> detectorClass) throws Exception {
			if(Objects.nonNull(fileDirToScan)) {
				String[] files = fileDirToScan.split(File.pathSeparator);
				this.metadataClass = metadataClass;
				this.fileConsumer = file -> Platform.runLater(() -> scanning.setText(file.getAbsolutePath()));
				Constructor<?> constructor = detectorClass.getConstructor(List.class, Consumer.class, Consumer.class);
				abstractFileDetector = (AbstractFileDetector) constructor.newInstance(com.anandhuarjunan.workspacetool.util.FileUtils.toFile(files), fileConsumer,null);
			}else {
				throw new Exception();
			}

		}

		private void sync() {

			Platform.runLater(
					() -> ongoingStatus.setText("Searching for "+"Java"));

			List<File> files = abstractFileDetector.detect();
			SessionFactory session = HibernateUtils.getSessionFactory();
			Session session2 = session.openSession();
			Transaction tx = session2.beginTransaction();
			files.forEach(file -> {
				Constructor<?> constructor = null;
				try {
					constructor = metadataClass.getConstructor(File.class);
					AbstractFileMetadata<?> fileMetadata = (AbstractFileMetadata<?>) constructor.newInstance(file);
					Platform.runLater(() -> ongoingStatus
							.setText("Detecting Metadata of " + "Java Env"));
					Optional<?> result = fileMetadata.start();
					session2.persist(result.get());

				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}

			});

			tx.commit();
			session2.close();
			Platform.runLater(() -> {
				onScanComplete(abstractFileDetector, files.size());
			});

		}

	}

	class SyncDirectoryBuilder {
		private List<SyncDirectory> syncDirectorys = null;
		private String[] tableNames = null;
		private List<Reloadable> reloadableControllers = null;


		public SyncDirectoryBuilder() {
			syncDirectorys =  new ArrayList<>();
			reloadableControllers = new ArrayList<>();

		}

		public SyncDirectoryBuilder ofTableName(String... tableName) {
			this.tableNames = tableName;
			return this;
		}


		public SyncDirectoryBuilder ofMetadata(
				Triplet<String,Class<? extends AbstractFileMetadata<?>>, Class<? extends AbstractFileDetector>> impl)
				throws Exception {
			syncDirectorys.add(new SyncDirectory(impl.getValue0(), impl.getValue1(), impl.getValue2()));
			return this;
		}

		public SyncDirectoryBuilder haveToReload(ReloadableViews reloadableViews) {
		//	this.reloadableControllers.add((Reloadable)dependencyControllers.get(reloadableViews));
			return this;
		}


		public void startSync() {


			Runnable bgThread = () -> {
				Util.hqlTruncate(tableNames);
				Platform.runLater(() -> {
					resultFlow.getChildren().clear();
					disableButtons();
					showProgessPane();
				});
				syncDirectorys.forEach(SyncDirectory::sync);

				Platform.runLater(() -> {
					reloadableControllers.forEach(Reloadable::reload);
					hideProgessPane();
					showResultPane();
				});

			};
			executorService.execute(bgThread);
		}




		private void showResultPane() {

			Action showAction = ()->mainWindow.setBottom(statusPane);
			AnimationUtils.addAnimating(showAction, AnimationUtils.fadeInAnimationSupplier(statusPane));

			Timer timer = new Timer();
			TimerTask task = new TimerTask()
			{
			        public void run()
			        {
			        	Platform.runLater(()->{
			        		if(null != mainWindow.getBottom()) {
				        		hideStatus();

			        		}
			        	});

			        }

			};
			timer.schedule(task,10000l);
		}



		private void showProgessPane() {
			JFXUtils.addNodeIfNotExists(progressContainer, ongoingStatus);
			JFXUtils.addNodeIfNotExists(progressContainer, scanning);
			JFXUtils.addNodeIfNotExists(progressContainer, progressBar);
		}



		private void disableButtons() {

			JFXUtils.disableNodes(jdkBtn,jdkSync);


		}
	}

}
