package com.anandhuarjunan.workspacetool.filescanner;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

public class EclipseWorkspaceDetector extends AbstractFileDetector {




	public EclipseWorkspaceDetector(File root, Consumer<File> allFileReadConsumer,
			Consumer<File> detectedFileConsumer) {
		super(root, allFileReadConsumer, detectedFileConsumer);
	}

	@Override
	protected boolean logic(File file) {
		if (file.isDirectory() && file.getName().equalsIgnoreCase(".metadata")) {

			File[] filess = file.listFiles();
			boolean isExistVerionIni = false;
			for (File fs : filess) {
				if (fs.getName().equalsIgnoreCase("version.ini")) {
					isExistVerionIni = true;
					break;
				}
			}

			if (isExistVerionIni) {
				addResult(file.getAbsoluteFile().getParentFile());

			}

			return true;
		}
		return false;
	}

	@Override
	public Optional<String> name() {

		return Optional.of("Eclipse Workspace");
	}

}
