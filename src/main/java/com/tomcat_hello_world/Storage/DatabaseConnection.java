package com.tomcat_hello_world.Storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
	protected static Connection initializeDatabase() throws SQLException, ClassNotFoundException
	{
		
		String dbDriver = "com.mysql.jdbc.Driver";
		String dbURL = "jdbc:mysql://localhost:3306/";
	
		String dbName = "tomcat";
		String dbUsername = "root";
		String dbPassword = "Ak2001@rox";

		Class.forName(dbDriver);
		Connection con = DriverManager.getConnection(dbURL + dbName,dbUsername,dbPassword);
		return con;
	}
}


