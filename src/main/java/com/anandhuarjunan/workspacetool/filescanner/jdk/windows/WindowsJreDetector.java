package com.anandhuarjunan.workspacetool.filescanner.jdk.windows;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.anandhuarjunan.workspacetool.filescanner.AbstractFileDetector;

public class WindowsJreDetector extends AbstractFileDetector {

	public WindowsJreDetector(List<File> root, Consumer<File> allFileReadConsumer, Consumer<File> detectedFileConsumer) {
		super(root, allFileReadConsumer, detectedFileConsumer);
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
