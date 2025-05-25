package com.dkte.pizzashop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dkte.pizzashop.entities.Customer;
import com.dkte.pizzashop.utils.DBUtils;

public class CustomerDao implements AutoCloseable {
	private Connection connection = null;

	public CustomerDao() throws SQLException {
		connection = DBUtils.getConnection();
	}

	public void insertCustomer(Customer customer) throws SQLException {
		String sql = "INSERT INTO customer(name ,email,password,mobile) VALUES(?,?,?,?)";
		try (PreparedStatement insertStatement = connection.prepareCall(sql)) {
			insertStatement.setString(1, customer.getName());
			insertStatement.setString(2, customer.getEmail());
			insertStatement.setString(3, customer.getPassword());
			insertStatement.setString(4, customer.getMobile());
			insertStatement.executeUpdate();
		}
	}

	public Customer selectCustomer(String email, String password) throws SQLException {
		String sql = "SELECT * FROM customer WHERE email=? AND password =? ";
		try (PreparedStatement selectStatement = connection.prepareCall(sql)) {
			selectStatement.setString(1, email);
			selectStatement.setString(2, password);
			ResultSet re = selectStatement.executeQuery();
			Customer customer = new Customer();
			if (re.next()) {
				customer.setCid(re.getInt(1));
				customer.setName(re.getString(2));
				customer.setEmail(re.getString(3));
				customer.setPassword(re.getString(4));
				customer.setMobile(re.getString(5));
				return customer;
			}
		}
		return null;
	}

	public List<Customer> displayAllCustomer() throws SQLException {
		List<Customer> customerList = new ArrayList<Customer>();
		String sql = "SELECT * FROM customer";
		try (PreparedStatement selectPizza = connection.prepareCall(sql)) {
			ResultSet re = selectPizza.executeQuery();
			while (re.next()) {
				Customer customer = new Customer();
				customer.setCid(re.getInt(1));
				customer.setName(re.getString(2));
				customer.setEmail(re.getString(3));
				customer.setPassword(re.getString(4));
				customer.setMobile(re.getString(5));
				customerList.add(customer);
			}
		}
		return customerList;
	}

	@Override
	public void close() throws SQLException {
		if (connection != null)
			connection.close();
	}
}
