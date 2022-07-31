package com.anandhuarjunan.workspacetool.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.persistence.Query;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.anandhuarjunan.workspacetool.HibernateUtils;
import com.anandhuarjunan.workspacetool.ResourcesLoader;
import com.anandhuarjunan.workspacetool.persistance.models.Ides;
import com.anandhuarjunan.workspacetool.persistance.models.JavaTypes;
import com.anandhuarjunan.workspacetool.persistance.models.KvStrSettings;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Util {



	public static int countFiles(File directory) {
	    int count = 0;
	    for (File file : directory.listFiles()) {
	        if (file.isDirectory()) {
	            count += countFiles(file);
	        }
	        count++;
	    }
	    return count;
	}

	public static String getSettings(String key) {
		 SessionFactory session =  HibernateUtils.getSessionFactory();
		 Session session2 = session.openSession();
		 KvStrSettings kvStrSettings =  session2.find(KvStrSettings.class, key);
		 String result = Objects.nonNull(kvStrSettings)?kvStrSettings.getValue():null;
		 session2.close();
		 return result;
	}

	public static Ides fetchIde(String key) {
		 SessionFactory session =  HibernateUtils.getSessionFactory();
		 Session session2 = session.openSession();
		 Ides ides =  session2.find(Ides.class, key);

		 session2.close();
		 return ides;
	}

	public static JavaTypes fetchJavaType(String key) {
		 SessionFactory session =  HibernateUtils.getSessionFactory();
		 Session session2 = session.openSession();
		 JavaTypes javaType =  session2.find(JavaTypes.class, key);

		 session2.close();
		 return javaType;
	}


	public static boolean isDataPresent(Class value, Object idValue) {
		 SessionFactory session =  HibernateUtils.getSessionFactory();
		 Session session2 = session.openSession();
		boolean result =  session2.createCriteria(value)
	            .add(Restrictions.idEq(idValue))
	            .setProjection(Projections.id())
	            .uniqueResult() != null;

		session2.close();
		return result;
	}

	public static void putSettings(String key,String value) {
		SessionFactory session =  HibernateUtils.getSessionFactory();
		 Session session2 = session.openSession();
		 Transaction tx = session2.beginTransaction();
		 KvStrSettings kvStrSettings =  new KvStrSettings();
		 kvStrSettings.setKey(key);
		 kvStrSettings.setValue(value);
		 session2.saveOrUpdate(kvStrSettings);
		 tx.commit();
		 session2.close();
	}


	public static String execToString(String command) throws Exception {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    CommandLine commandline = CommandLine.parse(command);
	    DefaultExecutor exec = new DefaultExecutor();
	    PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
	    exec.setStreamHandler(streamHandler);
	    exec.execute(commandline);
	    return(outputStream.toString());
	}

	public static String execToString(String command,Executor executor) throws Exception {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    CommandLine commandline = CommandLine.parse(command);
	    PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
	    executor.setStreamHandler(streamHandler);
	    executor.execute(commandline);
	    return(outputStream.toString());
	}

	public static int hqlTruncate(String myTable){
	    String hql = "truncate table "+myTable;
	    SessionFactory session =  HibernateUtils.getSessionFactory();
		 Session session2 = session.openSession();
		 Transaction tx = session2.beginTransaction();
	    Query query = session2.createNativeQuery(hql);
	   int d =  query.executeUpdate();
	    tx.commit();
	    session2.close();
	    return d;
	}

	public static void hqlTruncate(String[] myTables){

		for(String tableName : myTables) {
			hqlTruncate(tableName);
		}

	}

	public static Parent loadFxml(String resource) throws IOException {
		FXMLLoader loader = new FXMLLoader(ResourcesLoader.loadURL(resource));
		return loader.load();

	}
	public static FXMLLoader loadFxmlLoader(String resource)  {
		return new FXMLLoader(ResourcesLoader.loadURL(resource));

	}

	public static Parent loadFxml(String resource,Object controller) {
		 FXMLLoader loader = new FXMLLoader(ResourcesLoader.loadURL(resource));
			loader.setControllerFactory(c -> controller);
			try {
				return loader.load();
			}
			catch(Exception e) {
				return null;
			}
	}

}
