package persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingletonDatabaseConnection {
	 private static Connection connection;
	 static {
		  try {
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
		  } catch (Exception e) {
				e.printStackTrace();
		  }
	 }

	 public static Connection getConnection() {
		  return connection;
	 }
}
