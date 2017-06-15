package com.leonardortlima;

import com.leonardortlima.dao.Dao;
import com.leonardortlima.factory.UserDaoFactory;
import com.leonardortlima.model.User;
import com.leonardortlima.rest.filter.AccessControlFilter;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * @author leonardortlima
 * @since 2017-06-12
 */
public class Main {

  public static void main(String[] args) throws Exception {

    String webPort = System.getenv("PORT");
    if (webPort == null || webPort.isEmpty()) {
      webPort = "8080";
    }

    ResourceConfig config = new ResourceConfig()
        .packages("com.leonardortlima")
        .register(new AbstractBinder() {
          @Override protected void configure() {
            bindFactory(UserDaoFactory.class)
                .to(new TypeLiteral<Dao<User>>() {
                });
          }
        })
        .register(AccessControlFilter.class);
    ServletHolder servlet = new ServletHolder(new ServletContainer(config));

    Server server = new Server(Integer.parseInt(webPort));
    ServletContextHandler context = new ServletContextHandler(server, "/*");
    context.addServlet(servlet, "/*");

    try {
      server.start();
      server.join();
    } finally {
      server.destroy();
    }
  }
}
