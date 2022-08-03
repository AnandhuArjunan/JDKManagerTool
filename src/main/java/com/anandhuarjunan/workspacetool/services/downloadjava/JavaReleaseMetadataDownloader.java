package com.anandhuarjunan.workspacetool.services.downloadjava;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.anandhuarjunan.workspacetool.constants.Constants;

public class JavaReleaseMetadataDownloader {

	public static String METADATAURL = "https://joschi.github.io/java-metadata/metadata/all.json";
	private static String metadataFileName = "all.json";
	private static String userDir = System.getProperty("user.home");
	public static final String METADATAFILE = userDir+File.separator+Constants.USER_HOME_FOLDER+File.separator+metadataFileName;




	public File downloadMetadata() throws IOException {
	    File userPath = new java.io.File(userDir,Constants.USER_HOME_FOLDER);
	    userPath.mkdirs();
	    URL url = new URL(METADATAURL);
	    File metadataFilepath = new java.io.File(userDir,Constants.USER_HOME_FOLDER+File.separator+metadataFileName);
	    FileUtils.copyURLToFile(url, metadataFilepath);
	    return metadataFilepath;


	}



}
