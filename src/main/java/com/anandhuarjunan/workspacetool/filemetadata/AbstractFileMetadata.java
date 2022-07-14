package com.anandhuarjunan.workspacetool.filemetadata;

import java.io.File;
import java.util.Optional;

import com.anandhuarjunan.workspacetool.util.Namable;

public abstract class AbstractFileMetadata<T> implements Namable {

	protected File rootLoc = null;
	protected AbstractFileMetadata( File rootLoc) {
		this.rootLoc = rootLoc;
	}

	protected abstract boolean init();
	protected abstract T getMetadata();

	public Optional<T> start() {
		if(init()) {
			return Optional.ofNullable(getMetadata());
		}
		return Optional.empty();
	}




}
