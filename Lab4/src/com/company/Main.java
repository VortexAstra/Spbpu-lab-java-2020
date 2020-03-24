package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {
			ConnectDB.connect();
			ps = ConnectDB.connection.prepareStatement("INSERT INTO goods (prodid, title, cost) VALUES (?, ?, ?)");
			ConnectDB.connection.setAutoCommit(false);
			fill();
			ConnectDB.connection.setAutoCommit(true);

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Доступные команды: \n 1 - Show  \n 2 - Add  \n 3 - Delete \n 4 - Price \n 5 - ChangePrice " +
				"\n 6 - FilterByPrice \n 7 - Exit ");

		boolean flag = true;
		try {
			while (flag) {
				System.out.println("Enter your request");
				String s = in.next();
				switch (s) {
					case "Show":
						showAllInfoDB();
						break;
					case "Add":
						add();
						break;
					case "Delete":
						delete();
						break;
					case "Price":
						infoPrice();
						break;
					case "ChangePrice":
						changePrice();
						break;
					case "FilterByPrice":
						filterByPrice();
					case "Exit":
						ConnectDB.disconnect();
						flag = false;
						break;
				}
				in.nextLine();
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}


	static PreparedStatement ps;
	static Scanner in = new Scanner(System.in);

	static void fill() throws SQLException {

		for (int i = 1; i <= 3; i++) {
			ps.setInt(1, (i + 4200));
			ps.setString(2, "product" + i);
			ps.setInt(3, i * 10);
			ps.executeUpdate();
		}
		System.out.println("Fill BD");
	}

	static void add() throws SQLException {

		System.out.println("Write the name of the product");
		String title = in.nextLine();
		System.out.println("Введите prodid товара");
		String prodid = in.nextLine();
		System.out.println("Введите cost товара");
		String cost = in.nextLine();

		ps.setInt(1, Integer.parseInt((prodid)));
		ps.setString(2, title);
		ps.setInt(3, Integer.parseInt(cost));
		ps.executeUpdate();
		System.out.println("Success adding");
	}

	static void delete() throws SQLException {
		System.out.println("Enter the ID of the product you want to REMOVE");
		int id = in.nextInt();
		if (isExistItem(id)) {
			ps.executeUpdate("DELETE FROM Goods WHERE ID = " + id);
			System.out.println("Success deleting");
		} else
			System.out.println("DON't HAVE THIS ID!");
	}

	static void infoPrice() throws SQLException {
		System.out.println("Enter the name of the product, whose price you want to know");
		String s1 = in.nextLine();
		PreparedStatement preparedStatement = ConnectDB.connection.prepareStatement("select * " +
				"from Goods where title = ?");
		preparedStatement.setString(1, s1);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			System.out.println("cost - " + resultSet.getString("cost"));
		}
	}

	static void changePrice() throws SQLException {
		System.out.println("Enter the name of the product\n");
		String title = in.nextLine();
		if (isExistItem(title)) {
			System.out.print("Enter the new price\n");
			int newPrice = in.nextInt();
			ConnectDB.statement.executeUpdate("UPDATE test.Goods SET cost = " + newPrice +
					" WHERE title = '" + title + "'");
			System.out.println("Price changed successfully.");
		} else
			System.out.println("DON't HAVE THIS PRODUCT!");
	}

	static void filterByPrice() throws SQLException {
		float num1 = in.nextFloat();
		float num2 = in.nextFloat();
		if ((num1 > 0) & (num2 > 0)) {
			ResultSet resultSet= ConnectDB.statement.executeQuery("SELECT title " +
					"cost FROM goods WHERE cost BETWEEN " + num1 + " AND " + num2);
			if (resultSet.next()) {
				while (resultSet.next()) {
					System.out.println(resultSet.getString(1) + ", " + resultSet.getInt(2));
				}
			} else {
				System.out.println("Your query did not return results.");
			}
		} else {
			System.out.println("Enter positive numbers");
		}

	}

	static boolean isExistItem(int IDOfItem) {
		try {
			ResultSet resultSet = ConnectDB.statement.executeQuery("SELECT title, cost FROM test.goods " +
					"WHERE ID='" + IDOfItem + "';");
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	static boolean isExistItem(String title) {
		try {
			ResultSet resultSet = ConnectDB.statement.executeQuery("SELECT title, cost FROM test.goods " +
					"WHERE title='" + title + "';");
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	static void showAllInfoDB() throws SQLException {
		//Экземпляр ResultSet имеет указатель, который указывает на текущую строку в полученном множестве.
		ResultSet resultSet = ConnectDB.statement.executeQuery("select * from Goods");
		while (resultSet.next()) {
			System.out.println("ID - " + resultSet.getInt("id"));
			System.out.println("Prodid - " + resultSet.getInt("prodid"));
			System.out.println("Title - " + resultSet.getString("title"));
			System.out.println("Cost - " + resultSet.getInt("cost"));
			System.out.println("----------------------");
		}
	}

}