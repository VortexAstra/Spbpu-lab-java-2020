package com.company.controller;

import com.company.tableDataBase.TableData;
import com.company.dataBaseConnect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


//xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml"

public class Controller implements Initializable {

	@FXML
	private Label labelForWriteAction;

	//Called to initialize a controller after its root element has been
	//  completely processed.
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private ObservableList<TableData> obList = FXCollections.observableArrayList();


	@FXML
	private TextField prodidGUI;

	@FXML
	private TextField titleGUI;

	@FXML
	private TextField costGUI;

//	@FXML
//	private Button searchButton;

	@FXML
	private TextField profileTextField;

	@FXML
	private Slider slider;

	@FXML
	private TableView<TableData> table;

	@FXML
	private TableColumn<TableData, Integer> idColumn;

	@FXML
	private TableColumn<TableData, String> prodidColumn;

	@FXML
	private TableColumn<TableData, String> titleColumn;

	@FXML
	private TableColumn<TableData, Integer> costColumn;


	@FXML
	public void executeRequest() {
		try {
			String text = profileTextField.getText();
			switch (text) {
				case "Show":
					if (!table.getItems().isEmpty()) {
						for (int i = 0; i < table.getItems().size(); i++) {
							table.getItems().clear();
						}

					}
					printInfoOnTable();
					break;
				case "Add":
					Main.add();
					break;
				case "Delete":
					Main.delete();
					break;
				case "Price":
					Main.infoPrice();
					break;
				case "ChangePrice":
					Main.changePrice();
					break;
				case "FilterByPrice":
					Main.filterByPrice();
				case "Exit":
					ConnectDB.disconnect();
					System.out.println("End working..........");
					System.exit(0);
					break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void printInfoOnTable() throws SQLException {
		ResultSet rs = ConnectDB.connection.createStatement().executeQuery("select * from Goods");

		while (rs.next()) {

			obList.add(new TableData(rs.getInt("id"), rs.getString("prodid"),
					rs.getString("title"), rs.getInt("cost")));
		}

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		prodidColumn.setCellValueFactory(new PropertyValueFactory<>("prodid"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

		table.setItems(obList);
	}

	@FXML
	public void searchOnPrice() throws SQLException {
		String coefficientOfPrice = String.valueOf(slider.getValue());

		PreparedStatement preparedStatement = ConnectDB.connection.prepareStatement("select * " +
				"from Goods where cost = ?");
		preparedStatement.setString(1, coefficientOfPrice);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (!table.getItems().isEmpty()) {
			for (int i = 0; i < table.getItems().size(); i++) {
				table.getItems().clear();
			}

		}

		while (resultSet.next()) {

			obList.add(new TableData(resultSet.getInt("id"), resultSet.getString("prodid"),
					resultSet.getString("title"), resultSet.getInt("cost")));
		}

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		prodidColumn.setCellValueFactory(new PropertyValueFactory<>("prodid"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

		table.setItems(obList);
	}

	@FXML
	private void addOnGuiTable() throws SQLException {
		int prodid = Integer.parseInt(prodidGUI.getText());
		String title = String.valueOf(titleGUI.getText());
		int cost = Integer.parseInt(costGUI.getText());

		PreparedStatement ps = ConnectDB.connection.prepareStatement("INSERT INTO Goods " +
				"(prodid, title, cost) VALUES (?, ?, ?)");

		ps.setInt(1, prodid);
		ps.setString(2, title);
		ps.setInt(3, cost);
		ps.executeUpdate();
	printInfoOnTable();
		labelForWriteAction.setText("Success");
	}

	/**
	 * Не работает
	 */
	@FXML
	private void deleteOnGuiTable() throws SQLException {

		int prodid = Integer.parseInt(prodidGUI.getText());
		String title = String.valueOf(titleGUI.getText());
		int cost = Integer.parseInt(costGUI.getText());

		PreparedStatement ps = ConnectDB.connection.prepareStatement("DELETE INTO Goods " +
				"(prodid, title, cost) VALUES (?, ?, ?)");

		ps.setInt(1, prodid);
		ps.setString(2, title);
		ps.setInt(3, cost);
//		ps.executeUpdate();

//			Main.ps.executeUpdate("DELETE FROM Goods WHERE title = " + title);
		labelForWriteAction.setText("Deleted");
	}

}


