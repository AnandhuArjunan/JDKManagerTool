package com.anandhuarjunan.workspacetool.filescanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import com.anandhuarjunan.workspacetool.util.Namable;

public abstract class AbstractFileDetector implements Namable{

	private List<File> resultfiles = null;
	private Consumer<File> allFileReadConsumer = null;
	private Consumer<File> detectedFileConsumer = null;
	private File root = null;

	protected AbstractFileDetector(File root, Consumer<File> allFileReadConsumer,Consumer<File> detectedFileConsumer){
		resultfiles = new ArrayList<>();
		this.allFileReadConsumer = allFileReadConsumer;
		   this.detectedFileConsumer = detectedFileConsumer;
		   this.root = root;
	}



	public List<File> detect() {
	   resultfiles.clear();

	   find(root);
	   return resultfiles;
	}


	protected void find(File root) {
		File[] files = root.listFiles();
	    for (File file : files) {
	    	 if(Objects.nonNull(allFileReadConsumer)) {
	    		 allFileReadConsumer.accept(file);
	    	 }
	    	if(logic(file)) {
	    		continue;
	    	}
	    	recursion(file);
	    }
	}





	protected abstract boolean logic(File file);//boolean - no further scan is required on the sub directory of this folder.


	protected void recursion(File file) {
		if (file.listFiles() != null)
        	find(file);
	}

   protected void addResult(File identified) {
	   if(Objects.nonNull(detectedFileConsumer)) {
		   detectedFileConsumer.accept(identified);
	   }
	   resultfiles.add(identified);
   }


}
