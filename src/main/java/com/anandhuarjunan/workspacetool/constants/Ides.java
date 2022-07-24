package com.anandhuarjunan.workspacetool.constants;

public enum Ides {



	ECLIPSE_IDE("Eclipse IDE","Eclipse Foundation"),
	ANDROID_STUDIO_IDE("Android Studio","Google, JetBrains"),
	NETBEANS_IDE("Netbeans IDE","Apache Software Foundation; Oracle Corporation"),
	STS_IDE("Spring Tool Suite","VMware"),
	INTELLIJ_IDE("IntelliJ IDEA"," JetBrains");


	String name = null;
	String developer=null;

	private Ides(String name, String founder) {
		this.name = name;
		this.developer = founder;
	}

	public String getName() {
		return name;
	}

	public String getDeveloper() {
		return developer;
	}


}
