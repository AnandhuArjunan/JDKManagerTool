package com.anandhuarjunan.workspacetool.filemetadata;

import java.io.File;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;
import com.anandhuarjunan.workspacetool.persistance.models.JavaTypes;
import com.anandhuarjunan.workspacetool.util.Util;

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
		JavaTypes javaTypes = Util.fetchJavaType("JRE");
		javaEnv.setJavaHome(rootLoc.getAbsoluteFile().getParentFile().getAbsoluteFile().getParentFile().getAbsolutePath());
		javaEnv.setExecutableLoc(rootLoc.getAbsoluteFile().getParentFile().getAbsoluteFile().getParentFile().getAbsolutePath()+File.separator+"bin"+File.separator);
		javaEnv.setJavaType(javaTypes);
		String line = javaEnv.getExecutableLoc()+"java.exe -version";

		try {
			String versionInfo = Util.execToString(line);
			String[] info = versionInfo.split("\n");
			javaEnv.setCompany(info[1]);
			Pattern p = Pattern.compile("\"([^\"]*)\"");
			Matcher m = p.matcher(info[0]);
			javaEnv.setVersion(m.find()? m.group(1):null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return javaEnv;

	}



}
