package com.anandhuarjunan.workspacetool.filemetadata;

import java.io.File;
import java.util.Optional;

import com.anandhuarjunan.workspacetool.persistance.models.WindowsIDE;

public class WindowsAndroidStudioMetadata extends AbstractFileMetadata<WindowsIDE>{

	public WindowsAndroidStudioMetadata(File rootLoc) {
		super(rootLoc);
	}

	@Override
	public Optional<String> name() {
		return Optional.of("Android Studio");
	}

	@Override
	protected boolean init() {
		return true;
	}

	@Override
	protected WindowsIDE getMetadata() {
		WindowsIDE ide = new WindowsIDE();
		ide.setName(rootLoc.getName());
		ide.setLocation(rootLoc.getAbsolutePath());

		return ide;
	}

}
