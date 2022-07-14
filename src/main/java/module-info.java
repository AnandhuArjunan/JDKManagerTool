module com.anandhuarjunan.workspacetool{
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
	requires ExtractPrintableTextLib;
	requires org.apache.commons.io;
	requires MaterialFX;
	requires org.hibernate.orm.core;
	requires java.sql;
	requires java.persistence;
	requires net.bytebuddy;
	requires java.xml.bind;
	requires com.sun.xml.bind;
	requires com.fasterxml.classmate;
	requires java.naming;
	requires java.management;
	requires javatuples;

	opens com.anandhuarjunan.workspacetool.controller to javafx.fxml;
	opens com.anandhuarjunan.workspacetool.controller.choosedir to javafx.fxml;
	opens com.anandhuarjunan.workspacetool.controller.workspaces to javafx.fxml;
	opens com.anandhuarjunan.workspacetool.controller.ide to javafx.fxml;
	opens com.anandhuarjunan.workspacetool.controller.jdk to javafx.fxml;


	opens com.anandhuarjunan.workspacetool.persistance.models to org.hibernate.orm.core;

	exports com.anandhuarjunan.workspacetool to  javafx.graphics;
	exports com.anandhuarjunan.workspacetool.controller to javafx.fxml;
}
