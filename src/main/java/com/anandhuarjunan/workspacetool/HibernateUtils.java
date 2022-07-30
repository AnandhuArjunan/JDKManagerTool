
package com.anandhuarjunan.workspacetool;

import java.util.Objects;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.anandhuarjunan.workspacetool.persistance.models.Ides;
import com.anandhuarjunan.workspacetool.persistance.models.JavaEnv;
import com.anandhuarjunan.workspacetool.persistance.models.JavaTypes;
import com.anandhuarjunan.workspacetool.persistance.models.KvStrSettings;
import com.anandhuarjunan.workspacetool.persistance.models.WindowsIDE;
import com.anandhuarjunan.workspacetool.persistance.models.Workspaces;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class HibernateUtils {

	    private static final SessionFactory sessionFactory;

		static {
			try {

				Configuration configuration = new Configuration();

				String dbHome = System.getProperty("h2.system.home");
				String jdbcUrl = null;
				if (Objects.nonNull(dbHome)) {
					jdbcUrl = "jdbc:h2:./" + dbHome + "/workspacedb";
				} else {
					jdbcUrl = "jdbc:h2:./app/workspacedb";
				}

				Properties settings = new Properties();
				settings.put(AvailableSettings.DRIVER, "org.h2.Driver");
				settings.put(AvailableSettings.URL, jdbcUrl);
				settings.put(AvailableSettings.USER, "root");
				settings.put(AvailableSettings.PASS, "root");
				settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.H2Dialect");

				settings.put(AvailableSettings.SHOW_SQL, "true");
				settings.put(AvailableSettings.FORMAT_SQL, "false");

				configuration.setProperties(settings);

				configuration.addAnnotatedClass(Workspaces.class);
				configuration.addAnnotatedClass(KvStrSettings.class);
				configuration.addAnnotatedClass(WindowsIDE.class);
				configuration.addAnnotatedClass(JavaEnv.class);
				configuration.addAnnotatedClass(Ides.class);
				configuration.addAnnotatedClass(JavaTypes.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			} catch (Exception ex) {
				throw new ExceptionInInitializerError(ex);
			}
		}

	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }



}
