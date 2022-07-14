package com.anandhuarjunan.workspacetool.filemetadata;

import java.io.File;
import java.util.Optional;

import com.anandhuarjunan.workspacetool.persistance.models.Workspaces;

public class WindowsEclipseWorkspaceMetadata extends AbstractFileMetadata<Workspaces> {

	public WindowsEclipseWorkspaceMetadata(File rootLoc) {
		super(rootLoc);
	}

	@Override
	public Optional<String> name() {
		return Optional.of("Eclipse IDE");
	}

	@Override
	protected boolean init() {
		return true;
	}

	@Override
	protected Workspaces getMetadata() {
		Workspaces workspaces = new Workspaces();

		File workspaceLoc = rootLoc.getAbsoluteFile().getParentFile();
	 	workspaces.setName(workspaceLoc.getAbsoluteFile().getParentFile().getName()+File.separator+workspaceLoc.getName());
	  	workspaces.setLocation(workspaceLoc.getAbsolutePath());
	  	return workspaces;
	}

}
