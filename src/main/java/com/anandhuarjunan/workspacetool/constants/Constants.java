package com.anandhuarjunan.workspacetool.constants;

import java.io.File;

public class Constants {



	public static final String ROOT_LOCATION = "ROOT_LOC";
	public static final String IDE_LOCATION = "IDE_LOC";
	public static final String JDK_LOCATION = "JDK_LOC";
	public static final String WORKSPACE_LOCATION = "WORKSPACE_LOC";


	public static final String USER_HOME =  System.getProperty("user.home");

	public static final String USER_HOME_FOLDER_NAME = "jdkmanager";
	public static final String RELEASE_DOWNLOAD_LOC =USER_HOME+File.separator+USER_HOME_FOLDER_NAME+File.separator+"downloads";
	public static final String METADTA_DOWNLOAD_LOC = USER_HOME+File.separator+USER_HOME_FOLDER_NAME;
	public static final String METADATAURL = "https://sadguruquotes.000webhostapp.com/jdk.json";




}
