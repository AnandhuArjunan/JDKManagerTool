package com.anandhuarjunan.workspacetool.filescanner.ide;

import java.io.File;
import java.util.function.Consumer;

import com.anandhuarjunan.workspacetool.filescanner.AbstractFileDetector;

public abstract class IDEDetector extends AbstractFileDetector {

	protected IDEDetector(File root, Consumer<File> allFileReadConsumer, Consumer<File> detectedFileConsumer) {
		super(root, allFileReadConsumer, detectedFileConsumer);
	}

}
