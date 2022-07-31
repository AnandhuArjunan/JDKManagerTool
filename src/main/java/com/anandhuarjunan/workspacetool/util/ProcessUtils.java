package com.anandhuarjunan.workspacetool.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.javatuples.Pair;

public class ProcessUtils {


	public static Pair<Integer,String> executeCommand(File workDirectory,String ...commands) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(commands);
		 processBuilder.directory(workDirectory);
		 processBuilder.redirectErrorStream(true);
		 Process process =  processBuilder.start();
		 String result = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
		 int exitCode = process.waitFor();
		 return new Pair<>(exitCode,result );
	}
}
