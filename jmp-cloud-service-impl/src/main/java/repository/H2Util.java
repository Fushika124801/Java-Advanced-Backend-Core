package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Util {

  private static final String jdbcURL = "jdbc:h2:~/test";
  private static final String jdbcUsername = "sa";
  private static final String jdbcPassword = "";

  public static Connection getConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }
}
