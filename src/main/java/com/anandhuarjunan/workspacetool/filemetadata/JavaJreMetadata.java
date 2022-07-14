package com.anandhuarjunan.workspacetool.filemetadata;

import java.io.File;
import java.util.Optional;

import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;

public class JavaJreMetadata extends AbstractFileMetadata<JavaEnv> {

	public JavaJreMetadata(File rootLoc) {
		super(rootLoc);
	}

	@Override
	public Optional<String> name() {
		return Optional.of("JRE");
	}

	@Override
	protected boolean init() {
		return true;
	}

	@Override
	protected JavaEnv getMetadata() {
		JavaEnv javaEnv = new JavaEnv();
		javaEnv.setName(rootLoc.getName());
		javaEnv.setExecutableLoc(rootLoc.getAbsolutePath());
		return javaEnv;

	}



}
