package com.anandhuarjunan.workspacetool.filemetadata;

import java.io.File;
import java.util.Optional;

import com.anandhuarjunan.workspacetool.persistance.models.WindowsIDE;
import com.anandhuarjunan.workspacetool.util.Util;

public class WindowsEclipseIDEMetadata extends AbstractFileMetadata<WindowsIDE> {

	public WindowsEclipseIDEMetadata(File rootLoc) {
		super(rootLoc);
	}

	@Override
	protected boolean init() {
		return true;
	}

	@Override
	protected WindowsIDE getMetadata() {

		WindowsIDE ide = new WindowsIDE();
		ide.setLocation(rootLoc.getAbsolutePath());
		ide.setIde(Util.fetchIde("ECLIPSE"));

		return ide;
	}

	@Override
	public Optional<String> name() {
		return Optional.of("Eclipse");
	}







}
