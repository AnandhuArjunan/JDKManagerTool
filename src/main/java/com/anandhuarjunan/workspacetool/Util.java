package com.anandhuarjunan.workspacetool;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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
