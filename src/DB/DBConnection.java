package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
  private static Connection conn;

  public static Connection getConnection() throws SQLException{
      try {
          Class.forName("com.mysql.cj.jdbc.Driver");
          conn = DriverManager.getConnection(Configuration.url, Configuration.userName, Configuration.password);
          return conn;
          
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
          return null;
        }
  }

  
}
