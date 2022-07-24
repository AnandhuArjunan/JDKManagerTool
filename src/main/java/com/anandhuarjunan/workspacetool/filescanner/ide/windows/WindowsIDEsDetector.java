package com.anandhuarjunan.workspacetool.filescanner.ide.windows;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import com.anandhuarjunan.workspacetool.filescanner.ide.IDEDetector;

public abstract class WindowsIDEsDetector extends IDEDetector {

	protected WindowsIDEsDetector(List<File> root, Consumer<File> allFileReadConsumer, Consumer<File> detectedFileConsumer) {
		super(root, allFileReadConsumer, detectedFileConsumer);
	}



}
