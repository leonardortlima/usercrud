package com.leonardortlima.dao;

import com.leonardortlima.model.Model;
import com.leonardortlima.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * @author leonardortlima
 * @since 2016-11-07
 */
public enum DatabaseUtil {
  INSTANCE;

  //Property based configuration
  private SessionFactory sessionFactory;

  private synchronized SessionFactory buildSessionFactory() {
    try {
      Configuration configuration = new Configuration();

      Properties props = loadDatabaseProperties();
      props.put("hibernate.current_session_context_class", "thread");
      props.put("show_sql", "true");

      configuration.setProperties(props);

      configuration.addAnnotatedClass(Model.class);
      configuration.addAnnotatedClass(User.class);

      ServiceRegistry serviceRegistry =
          new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
      System.out.println("Hibernate Java Config serviceRegistry created");

      return configuration.buildSessionFactory(serviceRegistry);
    } catch (Exception ex) {
      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public Session openSession() {
    if (sessionFactory == null || sessionFactory.isClosed()) {
      sessionFactory = buildSessionFactory();
    }
    return sessionFactory.openSession();
  }

  /**
   * Loads the database configuration either from env variables or properties files
   */
  public static Properties loadDatabaseProperties() throws Exception {
    String systemDatabaseURL = System.getenv("DATABASE_URL");
    Properties properties;

    if (systemDatabaseURL != null) {
      properties = loadFromEnv(systemDatabaseURL);
    } else {
      properties = loadFromProperties();
    }

    properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");

    return properties;
  }

  private static Properties loadFromEnv(String systemDatabaseURL) throws Exception {
    URI dbUri;
    try {
      dbUri = new URI(systemDatabaseURL);
    } catch (URISyntaxException e) {
      throw new Exception(e);
    }

    Properties properties = new Properties();

    properties.put(
        "hibernate.connection.url",
        "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath());
    properties.put("hibernate.connection.username", dbUri.getUserInfo().split(":")[0]);
    properties.put("hibernate.connection.password", dbUri.getUserInfo().split(":")[1]);

    return properties;
  }

  private static Properties loadFromProperties() throws Exception {
    InputStream inputStream = null;

    try {
      Properties properties = new Properties();
      String propFileName = "db.properties";

      inputStream = DatabaseUtil.class.getClassLoader().getResourceAsStream(propFileName);

      if (inputStream != null) {
        properties.load(inputStream);
      } else {
        throw new Exception("property file '" + propFileName + "' not found in the classpath");
      }

      if (!properties.containsKey("hibernate.connection.url")) {
        throw new Exception("db.properties missing url key");
      }

      if (!properties.containsKey("hibernate.connection.username")) {
        throw new Exception("db.properties missing username key");
      }

      if (!properties.containsKey("hibernate.connection.password")) {
        throw new Exception("db.properties missing password key");
      }

      return properties;
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace(System.err);
        }
      }
    }
  }
}
