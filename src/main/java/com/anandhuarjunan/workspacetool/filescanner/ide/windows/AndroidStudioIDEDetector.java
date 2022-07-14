package com.anandhuarjunan.workspacetool.filescanner.ide.windows;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

public class AndroidStudioIDEDetector extends WindowsIDEsDetector  {

	public AndroidStudioIDEDetector(File root, Consumer<File> allFileReadConsumer,
			Consumer<File> detectedFileConsumer) {
		super(root, allFileReadConsumer, detectedFileConsumer);
	}

	@Override
	protected boolean logic(File file) {
		if( file.isFile()
				&& ((file.getName().equalsIgnoreCase("studio.exe")))){
			addResult(file.getAbsoluteFile());
			return true;
		}
		return false;
	}

	@Override
	public Optional<String> name() {

		return Optional.of("Android Studio IDE");
	}

}
