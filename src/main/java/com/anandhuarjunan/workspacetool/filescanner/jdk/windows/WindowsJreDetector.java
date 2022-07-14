package com.anandhuarjunan.workspacetool.filescanner.jdk.windows;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

import com.anandhuarjunan.workspacetool.filescanner.AbstractFileDetector;

public class WindowsJreDetector extends AbstractFileDetector {

	public WindowsJreDetector(File root, Consumer<File> allFileReadConsumer, Consumer<File> detectedFileConsumer) {
		super(root, allFileReadConsumer, detectedFileConsumer);
	}

	@Override
	public Optional<String> name() {
		return Optional.of("Java Runtime [JRE]");
	}

	@Override
	protected boolean logic(File file) {
		if( file.isFile()
				&& ((file.getName().equalsIgnoreCase("javac.exe")))){
			addResult(file.getAbsoluteFile());
			return true;
		}
		return false;
	}

}
