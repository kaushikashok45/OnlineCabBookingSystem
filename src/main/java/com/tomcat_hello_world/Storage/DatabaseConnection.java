package com.tomcat_hello_world.Storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.tomcat_hello_world.Utility.Constants;

public class DatabaseConnection {
	protected static Connection initializeDatabase() throws SQLException, ClassNotFoundException
	{
		
		String dbDriver = Constants.dbDriver;
		String dbURL = Constants.dbURL;
	
		String dbName = Constants.dbName;
		String dbUsername = Constants.dbUsername;
		String dbPassword = Constants.dbPassword;

		Class.forName(dbDriver);
		Connection con = DriverManager.getConnection(dbURL + dbName,dbUsername,dbPassword);
		return con;
	}
}


