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
	Pair<Integer, String> versionInfo2 = getJavac();

	if(versionInfo2.getValue0() == 0) {
		String[] info2 = versionInfo2.getValue1().split(" ");
		map.put(JDK_VERSION,null != info2 && info2.length>1?info2[1].trim():"Not Found!");
	}else {
		map.put(JDK_VERSION,"Not Found!");

	}


	return map;
}

private static Pair<Integer,String> getJavac() throws IOException, InterruptedException {
	return ProcessUtils.executeCommand(new File(System.getProperty("user.home")), "javac","-version");
}
private static Pair<Integer,String> getJava() throws IOException, InterruptedException {
	return ProcessUtils.executeCommand(new File(System.getProperty("user.home")), "java","-version");

}

}