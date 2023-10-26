package com.anandhuarjunan.workspacetool.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.javatuples.Pair;

import com.anandhuarjunan.workspacetool.constants.CommonEnvHomes;

public class JavaEnvUtils {


public static final String JDK_VERSION = "JDK_VERSION";
public static final String JRE_VERSION = "JDK_VERSION";
public static final String VENDOR = "VENDOR";



public static Map<String,String> getJavaEnvDetails() throws Exception{

	Map<String,String> map = new HashMap<>();
	Pair<Integer, String> versionInfo = getJava();

	if(versionInfo.getValue0() == 0) {
		String[] info = versionInfo.getValue1().split("\n");
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m = p.matcher(info[0]);
		map.put(JRE_VERSION, m.find()? m.group(1).trim():"Not Found!");
		map.put(VENDOR,info[1].trim());
	}
	else {
		map.put(JRE_VERSION,"Not Found!");
		map.put(VENDOR,"Not Found!");
	
	}


	return map;
}

private static Pair<Integer,String> getJava() throws IOException, InterruptedException {
	EnvironmentVariableUtils environmentUtils = new EnvironmentVariableUtils();
	String javaPathFromEnv = environmentUtils.getEnvValue(CommonEnvHomes.JAVA_HOME);
	System.out.println(javaPathFromEnv.trim()+File.separator+"bin");
	return ProcessUtils.executeCommand(new File(javaPathFromEnv.trim()+File.separator+"bin"+File.separator), "java","-version");

}

}