package com.dkte.pizzashop.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.dkte.pizzashop.dao.OrderDao;
import com.dkte.pizzashop.dao.PizzaDao;
import com.dkte.pizzashop.entities.Customer;
import com.dkte.pizzashop.entities.Order;
import com.dkte.pizzashop.entities.Pizza;

public class SubMenu {
	private static int subMenu(Scanner sc) {
		System.out.println("***********************");
		System.out.println("0) Logout");
		System.out.println("1) Pizza Menu");
		System.out.println("2) Order Pizza");
		System.out.println("3) Order History");
		System.out.println("***********************");
		System.out.print("Please select an option: ");
		return sc.nextInt();
	}

	private static int pizzaMenuList(Scanner sc) {
		System.out.println("_______________________");
		System.out.println("0. Exit Menu List");
		System.out.println("1. Veg Pizza");
		System.out.println("2. Non Veg Pizza");
		System.out.println("3. Garlic Bread");
		System.out.println("4. Drinks");
		System.out.println("_______________________");
		System.out.print("\"Please choose an Category: ");
		return sc.nextInt();
	}

	private static void pizzaMenu(Scanner sc) {
		int choice;
		String category = null;
		if ((choice = pizzaMenuList(sc)) != 0) {
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
				System.out.println("Invalaid selection. :(");
				break;
			}
		}
		List<Pizza> pizzaList = new ArrayList<Pizza>();
		try (PizzaDao pizzaDao = new PizzaDao()) {
			pizzaList = pizzaDao.displayAllPizza(category);
			System.out.println("-----------------------------------------------------------------------------------");
			pizzaList.forEach(p -> System.out.print(p));
			System.out.println("-----------------------------------------------------------------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void OrderPlaced(Scanner sc, Customer customer) {
		Order order = new Order();
		order.setCid(customer.getCid());
		System.out.println("Please enter the Pizza Menu ID: ");
		int id = sc.nextInt();
		order.setMid(id);
		try (OrderDao orderDao = new OrderDao()) {
			orderDao.insertOrder(order);
			System.out.println("Your order has been placed successfully...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void orderHistory(Customer customer) {
		List<Pizza> pizzaList = new ArrayList<Pizza>();
		try (OrderDao order = new OrderDao()) {
			pizzaList = order.getAllOrder(customer.getCid());
			if (pizzaList.isEmpty()) {
				System.out.println("You have no previous orders!!..");
			}
			pizzaList.forEach(p -> System.out.println(p));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void sub_menu(Customer customer, Scanner sc) {
		System.out.println("\n   Welcome " + customer.getName() + "!");
		int choice;

		while ((choice = subMenu(sc)) != 0) {
			switch (choice) {
			case 1:
				pizzaMenu(sc);
				break;
			case 2:
				OrderPlaced(sc, customer);
				break;
			case 3:
				System.out.println(customer.getName() + "'s" + " Order History\n");
				System.out.println("--------------------------------------------------------------------");
				orderHistory(customer);
				System.out.println("--------------------------------------------------------------------");
				break;
			default:
				System.out.println("Invalid selection. Please try again. :(");
				break;
			}
		}
		System.out.println("\nYou have successfully logged out...\n");
	}
}
