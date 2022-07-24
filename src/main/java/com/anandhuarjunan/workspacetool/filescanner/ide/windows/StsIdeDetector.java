package com.anandhuarjunan.workspacetool.filescanner.ide.windows;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.anandhuarjunan.workspacetool.util.FileUtils;

public class StsIdeDetector  extends WindowsIDEsDetector{

	public StsIdeDetector(List<File> root, Consumer<File> allFileReadConsumer, Consumer<File> detectedFileConsumer) {
		super(root, allFileReadConsumer, detectedFileConsumer);

	}

	@Override
	public Optional<String> name() {

		return Optional.of("Spring Tool Suite IDE");
	}

	@Override
	protected boolean logic(File file) {

		if(file.isFile() && file.getName().equalsIgnoreCase(".eclipseproduct")) {

			List<File> iniFile = FileUtils.getFilesWithExtension(file.getAbsoluteFile().getParentFile(), "ini");

			if(!iniFile.isEmpty()) {
				try {
					String readIni = org.apache.commons.io.FileUtils.readFileToString(iniFile.get(0), StandardCharsets.UTF_8);
					if(readIni.contains("org.springframework.boot.ide")) {
						addResult(file.getAbsoluteFile().getParentFile());
			    		return true;
					}
				} catch (IOException e) {

				}
			}

    	}
		return false;

	}

}
