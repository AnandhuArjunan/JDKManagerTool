package com.anandhuarjunan.workspacetool.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

public class FileUtils {



	public static List<File> toFile(String[] files) {
		List<File> arrfiles = new ArrayList<>();
		for(String file :files) {
			arrfiles.add(new File(file));
		}
		return arrfiles;
	}

	public static boolean isContains(File folder , String filename) {
		if(folder.isDirectory()) {
			File[] files = folder.listFiles();
			for(File f : files) {
				if(f.getName().equalsIgnoreCase(filename)) {
					return true;
				}
			}
		}
		return false;
	}

	public static List<File> getFilesWithExtension(File folder , String filextension) {
		List<File> rfiles = new ArrayList<>();
		if(folder.isDirectory()) {
			File[] files = folder.listFiles();
			for(File f : files) {
				if(FilenameUtils.getExtension(f.getName()).equalsIgnoreCase(filextension)) {
					rfiles.add(f);
				}
			}
		}
		return rfiles;
	}

}
