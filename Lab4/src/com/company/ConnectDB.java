package com.company;

import java.sql.*;

import static java.lang.System.currentTimeMillis;

public class ConnectDB {

	protected static Connection connection;
	protected static Statement statement;

	static void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		ConnectDB.connection = DriverManager.getConnection(Metadata.connectionURL, Metadata.user, Metadata.password);
		System.out.println("We're connected");

		statement = ConnectDB.connection.createStatement();
		statement.executeUpdate("drop table IF EXISTS Goods");
		statement.execute("CREATE TABLE IF NOT EXISTS test.goods (id INT NOT NULL AUTO_INCREMENT, " +
				"prodid INT NOT NULL, title VARCHAR(80) NOT NULL, cost DECIMAL(10,2) NOT NULL, PRIMARY KEY (id));");

		PreparedStatement ps = ConnectDB.connection.prepareStatement("INSERT INTO goods (prodid, title, cost) VALUES (?, ?, ?)");
		ConnectDB.connection.setAutoCommit(false);
		long t1 = currentTimeMillis();
	}

	public static void disconnect() throws SQLException {

		connection.close();

	}


}
