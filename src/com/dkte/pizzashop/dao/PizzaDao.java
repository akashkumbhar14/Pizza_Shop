package com.dkte.pizzashop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dkte.pizzashop.entities.Pizza;
import com.dkte.pizzashop.utils.DBUtils;

public class PizzaDao implements AutoCloseable {
	private Connection connection = null;

	public PizzaDao() throws SQLException {
		connection = DBUtils.getConnection();
	}

	public List<Pizza> displayAllPizza(String category) throws SQLException {
		List<Pizza> pizzaList = new ArrayList<Pizza>();
		String sql = "SELECT * FROM menu WHERE category = ?";
		try (PreparedStatement selectPizza = connection.prepareCall(sql)) {
			selectPizza.setString(1, category);
			ResultSet re = selectPizza.executeQuery();
			while (re.next()) {
				Pizza pizza = new Pizza();
				pizza.setMid(re.getInt(1));
				pizza.setName(re.getString(2));
				pizza.setDescription(re.getString(3));
				pizza.setPrice(re.getDouble(5));
				pizzaList.add(pizza);
			}
		}
		return pizzaList;
	}

	public void insertPizza(Pizza pizza, String category) throws SQLException {
		String sql = "INSERT INTO menu(name ,description,category,price) VALUES(?,?,?,?)";
		try (PreparedStatement insertStatement = connection.prepareCall(sql)) {
			insertStatement.setString(1, pizza.getName());
			insertStatement.setString(2, pizza.getDescription());
			insertStatement.setString(3, category);
			insertStatement.setDouble(4, pizza.getPrice());
			insertStatement.executeUpdate();
		}
	}

	public void updatePrice(int mid, double price) throws SQLException {
		String sql = "UPDATE menu SET price = ? WHERE mid= ? ";
		try (PreparedStatement updateStatement = connection.prepareCall(sql)) {
			updateStatement.setDouble(1, price);
			updateStatement.setInt(2, mid);
			updateStatement.executeUpdate();
		}
	}

	@SuppressWarnings("resource")
	public boolean deletePizza(int mid) throws SQLException {
		boolean result = new OrderDao().isInOrder(mid);
		if (result == true) {
			String sql = "DELETE FROM menu WHERE mid = ? ";
			try (PreparedStatement deleteStatement = connection.prepareCall(sql)) {
				deleteStatement.setInt(1, mid);
				deleteStatement.executeUpdate();
				return true;
			}
		}
		return false;
	}

	@Override
	public void close() throws SQLException {
		if (connection != null)
			connection.close();
	}
}
