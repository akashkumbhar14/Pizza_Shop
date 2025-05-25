package com.dkte.pizzashop.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.dkte.pizzashop.dao.CustomerDao;
import com.dkte.pizzashop.dao.OrderDao;
import com.dkte.pizzashop.dao.PizzaDao;
import com.dkte.pizzashop.entities.Customer;
import com.dkte.pizzashop.entities.OrderDetalis;
import com.dkte.pizzashop.entities.Pizza;

public class AdminMenu {
	private static int adminMenu(Scanner sc) {
		System.out.println("------------------------------");
		System.out.println("0) Exit");
		System.out.println("1) Add new Pizza");
		System.out.println("2) Update Pizza Price");
		System.out.println("3) Display all Customer");
		System.out.println("4) Display all Orders");
		System.out.println("5) Calculate total Profit");
		System.out.println("6) Delete Pizza:");
		System.out.println("------------------------------");
		System.out.print("Enter Your Choice: ");
		return sc.nextInt();
	}

	private static void addPizza(Scanner sc) {
		Pizza pizza = new Pizza();
		String category = null;
		int choice = 0;
		System.out.println("0. Exit Menu List");
		System.out.println("1. Veg Pizza");
		System.out.println("2. Non Veg Pizza");
		System.out.println("3. Garlic Bread");
		System.out.println("4. Drinks");
		System.out.print("Enter your choice: ");
		choice = sc.nextInt();
		if (choice != 0) {
			switch (choice) {
			case 1:
				category = "Veg Pizza";
				break;
			case 2:
				category = "Non Veg Pizza";
				break;
			case 3:
				category = "Garlic Bread";
				break;
			case 4:
				category = "Drinks";
				break;
			default:
				System.out.println("Invalaid Category  :(");
				break;
			}
			pizza.accept(sc);
			try (PizzaDao pizzaDao = new PizzaDao()) {
				pizzaDao.insertPizza(pizza, category);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void updatePizzaPrice(Scanner sc) {
		System.out.println("Enter the Menu ID: ");
		int mid = sc.nextInt();
		System.out.println("Enter the New Price: ");
		double price = sc.nextDouble();
		try (PizzaDao pizzaDao = new PizzaDao()) {
			pizzaDao.updatePrice(mid, price);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void displayCustomers() {
		List<Customer> customerList = new ArrayList<Customer>();
		try (CustomerDao customerDao = new CustomerDao()) {
			customerList = customerDao.displayAllCustomer();
			customerList.forEach(p -> System.out.print(p + "\n"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void totalProfit() {
		try (OrderDao o = new OrderDao()) {
			System.out.println(o.total_profit());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void displayAllOrders() {
		List<OrderDetalis> odetails = new ArrayList<OrderDetalis>();
		try (OrderDao orderDao = new OrderDao()) {
			odetails = orderDao.allOrders();
			odetails.forEach(p -> System.out.println(p));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void deletePizza(Scanner sc) {
		System.out.println("Which pizza would you like to delete?");
		System.out.print("Enter its Menu ID to proceed : ");

		int id = sc.nextInt();
		try (PizzaDao pizzaDao = new PizzaDao()) {
			boolean result = pizzaDao.deletePizza(id);
			if (result)
				System.out.println("Pizza Deleted Successful...");
			else
				System.out.println("This pizza cannot be deleted as it has already been ordered ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void adminMain(Scanner sc) {
		int choice;
		while ((choice = adminMenu(sc)) != 0) {
			switch (choice) {
			case 1:
				addPizza(sc);
				System.out.println("Pizza Added Successful...");

				break;
			case 2:
				updatePizzaPrice(sc);
				System.out.println("Price Updated...");
				break;
			case 3:
				displayCustomers();
				break;
			case 4:
				displayAllOrders();
				break;
			case 5:
				totalProfit();
				break;
			case 6:
				deletePizza(sc);
				break;
			default:
				System.out.println("Invalid Choice...");
				break;
			}
		}
	}
}
