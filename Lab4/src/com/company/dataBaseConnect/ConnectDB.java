package com.company.dataBaseConnect;

import java.sql.*;

public class ConnectDB {

	public static Connection connection;
	public static Statement statement;

	public static void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		ConnectDB.connection = DriverManager.getConnection(MetadataBD.connectionURL, MetadataBD.user, MetadataBD.password);
		System.out.println("We're connected");

		statement = ConnectDB.connection.createStatement();
		statement.executeUpdate("drop table IF EXISTS Goods");
		statement.execute("CREATE TABLE IF NOT EXISTS test.goods (id INT NOT NULL AUTO_INCREMENT, " +
				"prodid INT NOT NULL, title VARCHAR(80) NOT NULL, cost DECIMAL(10,2) NOT NULL, PRIMARY KEY (id));");

		ConnectDB.connection.setAutoCommit(false);
	}

	public static void disconnect() throws SQLException {
		connection.close();
	}
}
