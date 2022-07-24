package com.anandhuarjunan.workspacetool.filescanner.jdk.windows;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.anandhuarjunan.workspacetool.filescanner.AbstractFileDetector;

public class WindowsJdkDetector extends AbstractFileDetector {

	public WindowsJdkDetector(List<File> root, Consumer<File> allFileReadConsumer, Consumer<File> detectedFileConsumer) {
		super(root, allFileReadConsumer, detectedFileConsumer);
	}

	@Override
	public Optional<String> name() {
		return Optional.of("Java Compiler [JDK]");
	}

	@Override
	protected boolean logic(File file) {
		if( file.isFile()
				&& ((file.getName().equalsIgnoreCase("java.exe")))){
			addResult(file.getAbsoluteFile());
			return true;
		}
		return false;
	}


}
