package com.anandhuarjunan.workspacetool.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.anandhuarjunan.workspacetool.ResourcesLoader;


public class EnvironmentVariableUtils {

		public enum VariableTypes{
			USER,MACHINE
		}
		private File executablePath = null;

		public EnvironmentVariableUtils() {
			String runByGradle  = System.getProperty("Run");
			if(null == runByGradle) {
				executablePath = new File("app/eenv.exe");
			}else {
				executablePath = new File("native/eenv.exe");
			}
		}


		public int setEnvVariable(String name,String value) throws IOException, InterruptedException  {
			value = "\""+value+"\"";
			 File file = executablePath.getAbsoluteFile();
			 ProcessBuilder processBuilder = new ProcessBuilder(file.getAbsolutePath(),"set",name,value);
			 processBuilder.directory(file.getParentFile());
			 Process process =  processBuilder.start();
			 return process.waitFor();

		}

		public String getEnvValue(String name) throws IOException  {
			 File file = executablePath.getAbsoluteFile();
			 ProcessBuilder processBuilder = new ProcessBuilder(file.getAbsolutePath(),"get",name);
			 processBuilder.directory(file.getParentFile());
			 Process process =  processBuilder.start();
			 return IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);

		}



}
