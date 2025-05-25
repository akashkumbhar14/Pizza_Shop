package com.dkte.pizzashop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dkte.pizzashop.entities.Order;
import com.dkte.pizzashop.entities.OrderDetalis;
import com.dkte.pizzashop.entities.Pizza;
import com.dkte.pizzashop.utils.DBUtils;

public class OrderDao implements AutoCloseable {
	private Connection connection = null;

	public OrderDao() throws SQLException {
		connection = DBUtils.getConnection();
	}

	public void insertOrder(Order order) throws SQLException {
		String sql = "INSERT INTO orders(cid, mid)VALUES(?,?);";
		try (PreparedStatement insertOrder = connection.prepareCall(sql)) {
			insertOrder.setInt(1, order.getCid());
			insertOrder.setInt(2, order.getMid());
			insertOrder.executeUpdate();
		}
	}

	public List<Pizza> getAllOrder(int cid) throws SQLException {
		List<Pizza> pizzaList = new ArrayList<Pizza>();
		String sql = "SELECT m.* FROM menu m INNER JOIN orders o ON m.mid = o.mid WHERE cid = ?";
		try (PreparedStatement orderHisStatement = connection.prepareCall(sql)) {
			orderHisStatement.setInt(1, cid);
			ResultSet re = orderHisStatement.executeQuery();
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

	public List<OrderDetalis> allOrders() throws SQLException {
		List<OrderDetalis> orderList = new ArrayList<OrderDetalis>();
		String sql = "SELECT o.oid , c.cid,c.name,  m.name , m.price FROM customer c JOIN orders o JOIN menu m on o.mid = m.mid AND  o.cid = c.cid ORDER BY o.oid";
		try (PreparedStatement orderDetalis = connection.prepareCall(sql)) {
			ResultSet re = orderDetalis.executeQuery();
			while (re.next()) {
				OrderDetalis odetails = new OrderDetalis();
				odetails.setOid(re.getInt(1));
				odetails.setCid(re.getInt(2));
				odetails.setcName(re.getString(3));
				odetails.setmNname(re.getString(4));
				odetails.setPrice(re.getDouble(5));
				orderList.add(odetails);
			}
		}
		return orderList;
	}

	public double total_profit() throws SQLException {
		double profit = 0;
		String sql = "SELECT SUM(m.price) FROM orders o JOIN  menu m ON o.mid = m.mid;";
		try (PreparedStatement orderHisStatement = connection.prepareCall(sql)) {
			ResultSet re = orderHisStatement.executeQuery();
			if (re.next())
				profit = re.getDouble(1);
		}
		return profit;
	}

	public boolean isInOrder(int mid) throws SQLException {
		String sql = "SELECT COUNT(*) FROM orders WHERE mid = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, mid);
			ResultSet re = stmt.executeQuery();
			if (re.next()) {
				int count = re.getInt(1);
				if (count < 1)
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
