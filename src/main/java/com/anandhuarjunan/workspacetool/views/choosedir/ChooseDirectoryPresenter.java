package com.anandhuarjunan.workspacetool.views.choosedir;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
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
import com.anandhuarjunan.workspacetool.filemetadata.JavaJdkMetadata;
import com.anandhuarjunan.workspacetool.filemetadata.JavaJreMetadata;
import com.anandhuarjunan.workspacetool.filemetadata.WindowsAndroidStudioMetadata;
import com.anandhuarjunan.workspacetool.filemetadata.WindowsEclipseIDEMetadata;
import com.anandhuarjunan.workspacetool.filemetadata.WindowsEclipseWorkspaceMetadata;
import com.anandhuarjunan.workspacetool.filemetadata.WindowsStsIdeMetadata;
import com.anandhuarjunan.workspacetool.filescanner.AbstractFileDetector;
import com.anandhuarjunan.workspacetool.filescanner.EclipseWorkspaceDetector;
import com.anandhuarjunan.workspacetool.filescanner.ide.windows.AndroidStudioIDEDetector;
import com.anandhuarjunan.workspacetool.filescanner.ide.windows.EclipseIDEDetector;
import com.anandhuarjunan.workspacetool.filescanner.ide.windows.StsIdeDetector;
import com.anandhuarjunan.workspacetool.filescanner.jdk.windows.WindowsJdkDetector;
import com.anandhuarjunan.workspacetool.filescanner.jdk.windows.WindowsJreDetector;
import com.anandhuarjunan.workspacetool.util.Action;
import com.anandhuarjunan.workspacetool.util.AnimationUtils;
import com.anandhuarjunan.workspacetool.util.JFXUtils;
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
	private MFXButton ideBtn;

	@FXML
	private MFXTextField ideLoc;

	@FXML
	private MFXButton ideSync;

	@FXML
	private MFXButton jdkBtn;

	@FXML
	private MFXTextField jdkLoc;

	@FXML
	private MFXButton jdkSync;

	@FXML
	private Label ongoingStatus;

	@FXML
	private MFXButton rootBtn;

	@FXML
	private MFXTextField rootLoc;

	@FXML
	private MFXButton syncAll;

	@FXML
	private MFXButton syncRoot;

	@FXML
	private MFXButton workBtn;

	@FXML
	private MFXTextField workLoc;

	@FXML
	private MFXButton workSync;

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

		ideSync.setOnAction(ev ->startIdeSync());

		workSync.setOnAction(ev -> startWorkspaceSync());

		jdkSync.setOnAction(ev -> startJdkSync());

		syncAll.setOnAction(ev -> startRootSync());

	}

	private void enableButtons() {

		JFXUtils.enableNodes(workBtn,ideBtn,rootBtn,jdkBtn,syncAll,ideSync,jdkSync,workSync);

	}
	private void startRootSync() {

		try {
			SyncDirectoryBuilder directoryBuilder = new SyncDirectoryBuilder();
			directoryBuilder
					.ofTableName("JAVA_ENV","WINDOWS_IDE","Workspaces")
					.haveToReload(ReloadableViews.JDK).haveToReload(ReloadableViews.IDE).haveToReload(ReloadableViews.WORKSPACE)
					.ofMetadata(new Triplet<>(jdkLoc.getText(),JavaJdkMetadata.class, WindowsJdkDetector.class))
					.ofMetadata(new Triplet<>(jdkLoc.getText(),JavaJreMetadata.class, WindowsJreDetector.class))
					.ofMetadata(new Triplet<>(ideLoc.getText(),WindowsAndroidStudioMetadata.class, AndroidStudioIDEDetector.class))
					.ofMetadata(new Triplet<>(ideLoc.getText(),WindowsEclipseIDEMetadata.class, EclipseIDEDetector.class))
					.ofMetadata(new Triplet<>(ideLoc.getText(),WindowsStsIdeMetadata.class, StsIdeDetector.class))
					.ofMetadata(new Triplet<>(workLoc.getText(),WindowsEclipseWorkspaceMetadata.class, EclipseWorkspaceDetector.class))
					.startSync();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private void startJdkSync() {

		try {
			SyncDirectoryBuilder directoryBuilder = new SyncDirectoryBuilder();
			directoryBuilder
					.ofTableName("JAVA_ENV")
					.haveToReload(ReloadableViews.JDK)
					.ofMetadata(new Triplet<>(jdkLoc.getText(),JavaJdkMetadata.class, WindowsJdkDetector.class))
					.ofMetadata(new Triplet<>(jdkLoc.getText(),JavaJreMetadata.class, WindowsJreDetector.class))
					.startSync();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private void startIdeSync() {


		try {
			SyncDirectoryBuilder directoryBuilder = new SyncDirectoryBuilder();
			directoryBuilder.ofTableName("WINDOWS_IDE")
					.haveToReload(ReloadableViews.IDE)
					.ofMetadata(new Triplet<>(ideLoc.getText(),WindowsAndroidStudioMetadata.class, AndroidStudioIDEDetector.class))
					.ofMetadata(new Triplet<>(ideLoc.getText(),WindowsEclipseIDEMetadata.class, EclipseIDEDetector.class))
					.ofMetadata(new Triplet<>(ideLoc.getText(),WindowsStsIdeMetadata.class, StsIdeDetector.class))
					.startSync();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}



	private void startWorkspaceSync() {
		try {
			SyncDirectoryBuilder directoryBuilder = new SyncDirectoryBuilder();
			directoryBuilder
					.ofTableName("Workspaces")
					.haveToReload(ReloadableViews.WORKSPACE)
					.ofMetadata(new Triplet<>(workLoc.getText(),WindowsEclipseWorkspaceMetadata.class, EclipseWorkspaceDetector.class))
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

		Pair<String, TextField> workspaceNode = new Pair<>(Constants.WORKSPACE_LOCATION, workLoc);
		Pair<String, TextField> ideNode = new Pair<>(Constants.IDE_LOCATION, ideLoc);
		Pair<String, TextField> jdkNode = new Pair<>(Constants.JDK_LOCATION, jdkLoc);
		Pair<String, TextField> rootNode = new Pair<>(Constants.ROOT_LOCATION, rootLoc);


		addDirectoryChooser(workBtn, Arrays.asList(workspaceNode));
		addDirectoryChooser(ideBtn, Arrays.asList(ideNode));
		addDirectoryChooser(jdkBtn, Arrays.asList(jdkNode));
		addDirectoryChooser(rootBtn, Arrays.asList(workspaceNode, ideNode, jdkNode,rootNode));

	}

	private void addDirectoryChooser(Button clickAction, List<Pair<String, TextField>> textFields) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		clickAction.setOnAction(e -> {
			File selectedDirectory = directoryChooser.showDialog(clickAction.getScene().getWindow());
			if (Objects.nonNull(selectedDirectory)) {
				for (Pair<String, TextField> textField : textFields) {
					String content = textField.getValue().getText();
					if(content.trim().isEmpty() || rootBtn.getId().equalsIgnoreCase(clickAction.getId())) {
						textField.getValue().setText(selectedDirectory.getAbsolutePath());
					}else {
						textField.getValue().setText(content+File.pathSeparator+selectedDirectory.getAbsolutePath());

					}
					Util.putSettings(textField.getKey(), selectedDirectory.getAbsolutePath());
				}

			}

		});
	}

	private void initializeChooseDir() {
		loadSettingsFromDb();
	}

	private void loadSettingsFromDb() {
		rootLoc.setText(Util.getSettings(Constants.ROOT_LOCATION));
		workLoc.setText(Util.getSettings(Constants.WORKSPACE_LOCATION));
		ideLoc.setText(Util.getSettings(Constants.IDE_LOCATION));
		jdkLoc.setText(Util.getSettings(Constants.JDK_LOCATION));

	}

	public synchronized void onScanComplete(AbstractFileDetector abstractFileDetector, int fileFound) {

		FXMLLoader loader = new FXMLLoader(ResourcesLoader.loadURL("fxml/resultblock.fxml"));
		loader.setControllerFactory(
				c -> new StatusBlockPresenter(String.valueOf(fileFound), abstractFileDetector.name().orElseGet(() -> "")));
		try {
			Parent root = loader.load();
			resultFlow.getChildren().add(root);

		} catch (IOException e) {
			e.printStackTrace();
		}
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
					() -> ongoingStatus.setText("Searching for " + abstractFileDetector.name().orElseGet(() -> "")));

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
							.setText("Detecting Metadata of " + fileMetadata.name().orElseGet(() -> "")));
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
			this.reloadableControllers.add((Reloadable)dependencyControllers.get(reloadableViews));
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

			JFXUtils.disableNodes(workBtn,ideBtn,rootBtn,jdkBtn,syncAll,ideSync,jdkSync,workSync);


		}
	}

}
