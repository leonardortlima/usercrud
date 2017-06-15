package db.flyway;

import com.leonardortlima.dao.DatabaseUtil;
import java.util.Properties;
import org.flywaydb.core.Flyway;

/**
 * @author leonardortlima
 * @since 2017-06-12
 */
public class Migrations {

  public static void main(String[] args) throws Exception {
    Properties properties = DatabaseUtil.loadDatabaseProperties();

    Flyway flyway = new Flyway();
    flyway.setDataSource(
        properties.getProperty("hibernate.connection.url"),
        properties.getProperty("hibernate.connection.username"),
        properties.getProperty("hibernate.connection.password")
    );
    flyway.migrate();
  }
}
