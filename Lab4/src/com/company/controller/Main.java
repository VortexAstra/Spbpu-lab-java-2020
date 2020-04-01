package com.company.controller;

import com.company.dataBaseConnect.ConnectDB;
import com.company.property.PropertyColorPrint;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Scanner;

public class Main extends Application {

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
				"\n 6 - FilterByPrice  \n 7 - Graphics \n 8 - Exit ");

		try {
			while (true) {
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
					case "Graphics":
						launch(args);
					case "Exit":
						System.out.println("Disconnect....");
						ConnectDB.disconnect();
						System.exit(0);
						break;
					default:
						System.out.println(PropertyColorPrint.ANSI_RED + "ENTER CORRECT REQUEST!!!!" + PropertyColorPrint.ANSI_RED);
						System.out.println(PropertyColorPrint.ANSI_BLACK);
				}
				in.nextLine();
			}
		} catch (SQLException | NumberFormatException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private static void fill() throws SQLException {

		for (int i = 1; i <= 20; i++) {
			ps.setInt(1, (i + 4200));
			ps.setString(2, "product" + i);
			ps.setInt(3, i * 10);
			ps.executeUpdate();
		}
		System.out.println("Fill BD");
	}

	public static void add() throws SQLException {
		try {
			System.out.println("Write the name of the product");
			String title = in.nextLine();
			String titleOfProduct = in.nextLine();
			System.out.println("Write prodid of the product");
			int prodid = in.nextInt();
			System.out.println("Write cost of the product");
			int cost = in.nextInt();

			ps.setInt(1, prodid);
			ps.setString(2, titleOfProduct);
			ps.setInt(3, cost);
			ps.executeUpdate();
			System.out.println("Success adding");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public static void delete() throws SQLException {
		try {
			System.out.println("Enter the ID of the product you want to REMOVE");
			int id = in.nextInt();
			if (isExistItem(id)) {
				ps.executeUpdate("DELETE FROM Goods WHERE ID = " + id);
				System.out.println("Success deleting");
			} else
				System.out.println("DON't HAVE THIS ID!");
		} catch (NumberFormatException e) {
			System.out.println("Write the correct data");
		}

	}

	public static void infoPrice() throws SQLException {
		System.out.println("Enter the name of the product, whose price you want to know");
		String s1 = in.nextLine();
		String infoPrice = in.nextLine();
		PreparedStatement preparedStatement = ConnectDB.connection.prepareStatement("select * " +
				"from Goods where title = ?");
		preparedStatement.setString(1, infoPrice);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			System.out.println("Product is being searched......" + "\nProduct successfully found ");
			System.out.println("cost - " + resultSet.getString("cost"));
		}
	}

	public static void changePrice() throws SQLException {
		System.out.println("Enter the name of the product");
		String title = in.nextLine();
		String titleOfProduct = in.nextLine();
		if (isExistItem(titleOfProduct)) {
			System.out.print("Enter the new price\n");
			int newPrice = in.nextInt();
			ConnectDB.statement.executeUpdate("UPDATE test.Goods SET cost = " + newPrice +
					" WHERE title = '" + titleOfProduct + "'");
			System.out.println("Price changed successfully.");
		} else
			System.out.println("DON't HAVE THIS PRODUCT!");
	}

	public static void filterByPrice() throws SQLException {
		System.out.println("From");
		int firstNumber = in.nextInt();
		System.out.println("Before");
		int secondNumber = in.nextInt();
		if ((firstNumber >= 0) && (secondNumber >= 0)) {
			ResultSet resultSet = ConnectDB.statement.executeQuery("SELECT prodid, title " +
					"FROM goods WHERE cost BETWEEN " + firstNumber + " AND " + secondNumber);
			if (resultSet.next()) {
				while (resultSet.next()) {
					System.out.println("Prodid - " + resultSet.getString(1) + " Title - " +
							resultSet.getString(2));
				}
			} else {
				System.out.println("Your query did not return results.");
			}
		} else {
			System.out.println("Enter positive numbers");
		}
	}

	private static boolean isExistItem(int IDOfItem) {
		try {
			ResultSet resultSet = ConnectDB.statement.executeQuery("SELECT title, cost FROM test.goods " +
					"WHERE ID='" + IDOfItem + "';");
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isExistItem(String title) {
		try {
			ResultSet resultSet = ConnectDB.statement.executeQuery("SELECT title, cost FROM test.goods " +
					"WHERE title='" + title + "';");
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void showAllInfoDB() throws SQLException {
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

	static PreparedStatement ps;
	static Scanner in = new Scanner(System.in);

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("controller.fxml"));
		primaryStage.setTitle("GUI BD");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
