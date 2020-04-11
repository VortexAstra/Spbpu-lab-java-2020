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

/**
 * Для версии JV FX
 * xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml"
 */

public class Controller implements Initializable {

	//Called to initialize a controller after its root element has been
	//  completely processed.
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private ObservableList<TableData> obList = FXCollections.observableArrayList();

	@FXML
	public TextField titleForChangeProduct;
	@FXML
	public TextField newPrice;

	@FXML
	public TextField min;
	@FXML
	public TextField max;
	@FXML
	private Label labelForWriteAction;

	@FXML
	private TextField prodidGUI;

	@FXML
	private TextField titleGUI;

	@FXML
	private TextField costGUI;

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
					checkTableForEmptiness();
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

	public void printInfoOnTable() {
		try {
			checkTableForEmptiness();
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void searchOnPrice() {
		try {


			PreparedStatement preparedStatement = ConnectDB.connection.prepareStatement("select * " +
					"from Goods where cost = ?");
			preparedStatement.setString(1, String.valueOf(slider.getValue()));
			ResultSet resultSet = preparedStatement.executeQuery();

			checkTableForEmptiness();

			while (resultSet.next()) {
				obList.add(new TableData(resultSet.getInt("id"), resultSet.getString("prodid"),
						resultSet.getString("title"), resultSet.getInt("cost")));
			}
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			prodidColumn.setCellValueFactory(new PropertyValueFactory<>("prodid"));
			titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
			costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

			table.setItems(obList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void addOnGuiTable()  {
		try {
			PreparedStatement ps = ConnectDB.connection.prepareStatement("INSERT INTO Goods " +
					"(prodid, title, cost) VALUES (?, ?, ?)");

			ps.setInt(1, Integer.parseInt(prodidGUI.getText()));
			ps.setString(2, String.valueOf(titleGUI.getText()));
			ps.setInt(3, Integer.parseInt(costGUI.getText()));
			ps.executeUpdate();
			printInfoOnTable();
			labelForWriteAction.setText("Success add");
		} catch (NumberFormatException | SQLException e) {
			labelForWriteAction.setText("Write correct data");
		}
	}

	@FXML
	private void deleteOnGuiTable()  {
		try {
			labelForWriteAction.setText("");

			if (isExistItemProdid(prodidGUI.getText())) {
				labelForWriteAction.setText("Success delete");
			} else {
				labelForWriteAction.setText("Write correct data");
			}

			PreparedStatement ps = ConnectDB.connection.prepareStatement("INSERT INTO goods " +
					"prodid, title, cost) VALUES (?, ?, ?)");

			ps.executeUpdate("DELETE FROM Goods WHERE prodid = " +
					Integer.parseInt(prodidGUI.getText()));

			printInfoOnTable();
		} catch (NumberFormatException  | SQLException e) {
			labelForWriteAction.setText("Write correct data");
		}
	}

	@FXML
	public void searchPyCost() {
		try {
			labelForWriteAction.setText("");
			PreparedStatement ps = ConnectDB.connection.prepareStatement("SELECT * FROM Goods WHERE cost BETWEEN " +
					Integer.parseInt(min.getText()) + " AND " + Integer.parseInt(max.getText()));

			ResultSet resultSet = ps.executeQuery();
			checkTableForEmptiness();

			while (resultSet.next()) {
				obList.add(new TableData(resultSet.getInt("id"), resultSet.getString("prodid"),
						resultSet.getString("title"), resultSet.getInt("cost")));
			}
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			prodidColumn.setCellValueFactory(new PropertyValueFactory<>("prodid"));
			titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
			costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

			table.setItems(obList);
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
		}
	}


	@FXML
	public void changePrice() {
		try {
			if (isExistItemProdid(titleForChangeProduct.getText())) {
				ConnectDB.statement.executeUpdate("UPDATE test.Goods SET cost = " + newPrice.getText() +
						" WHERE prodid = '" + titleForChangeProduct.getText() + "'");
				printInfoOnTable();
			} else {
				labelForWriteAction.setText("Don't have this product");
			}
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
		}

	}

	private void checkTableForEmptiness() {
		if (!table.getItems().isEmpty()) {
			for (int i = 0; i < table.getItems().size(); i++) {
				table.getItems().clear();
			}

		}
	}

	boolean isExistItemProdid(String prodid) {
		try {
			ResultSet resultSet = ConnectDB.statement.executeQuery("SELECT * FROM test.goods " +
					"WHERE prodid=" + prodid + ";");
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


}


