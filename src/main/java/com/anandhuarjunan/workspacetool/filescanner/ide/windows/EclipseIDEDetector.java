package com.anandhuarjunan.workspacetool.filescanner.ide.windows;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.anandhuarjunan.workspacetool.util.FileUtils;

public class EclipseIDEDetector extends WindowsIDEsDetector {



	public EclipseIDEDetector(List<File> root, Consumer<File> allFileReadConsumer, Consumer<File> detectedFileConsumer) {
		super(root, allFileReadConsumer, detectedFileConsumer);
	}

	@Override
	protected boolean logic(File file) {
		if(file.isFile() && file.getName().equalsIgnoreCase(".eclipseproduct")) {
			if(FileUtils.isContains(file.getAbsoluteFile().getParentFile(), "eclipse.exe")) {
				addResult(file.getAbsoluteFile().getParentFile());
	    		return true;
			}

    	}
		return false;
	}


	@Override
	public Optional<String> name() {

		return Optional.of("Eclipse IDE");
	}
}
