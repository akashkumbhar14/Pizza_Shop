package com.dkte.pizzashop.main;

import com.dkte.pizzashop.dao.CustomerDao;
import com.dkte.pizzashop.entities.Customer;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
	private static int mainMenu(Scanner sc) {
		System.out.println("***********************");
		System.out.println("0) Exit");
		System.out.println("1) Login");
		System.out.println("2) Register");
		System.out.println("3) Admin Login");
		System.out.println("***********************");
		System.out.print("Please select an option: ");
//		int ans = 10;
//		if (sc.hasNextInt() && !sc.hasNextLine()) {
//			System.out.println("IN HAS");
//			ans = sc.nextInt();
//		}
		return sc.nextInt();
	}

	private static void login(Scanner sc) {
		System.out.println("");
		System.out.print("Enter E-mail/User Id:");
		String mail = sc.next();
		System.out.print("Enter Password:");
		String password = sc.next();
		try (CustomerDao customerDao = new CustomerDao()) {
			Customer customer = customerDao.selectCustomer(mail, password);
			if (customer != null)
				SubMenu.sub_menu(customer, sc);
			else
				System.out.println("\nInvalid credentials. Please try again... :(");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void adminLogin(Scanner sc) {
		Customer customer = new Customer();
		Customer admin = new Customer();
		admin.setEmail("admin@gmail.com");
		admin.setPassword("admin");
		System.out.print("Enter Admin Email: ");
		customer.setEmail(sc.next());
		System.out.print("Enter Admin Password: ");
		customer.setPassword(sc.next());

		if (customer.equals(admin))
			AdminMenu.adminMain(sc);
		else
			System.out.println("\\nInvalid Admin credentials. Please try again... :(");

	}

	private static void register(Scanner sc) {
		Customer customer = new Customer();
		customer.accept(sc);
		try (CustomerDao customerDao = new CustomerDao()) {
			customerDao.insertCustomer(customer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int choice;

		while ((choice = mainMenu(sc)) != 0) {
			switch (choice) {
			case 1:
				login(sc);
				break;
			case 2:
				register(sc);
				break;
			case 3:
				adminLogin(sc);
				break;
			default:
				System.out.println("Invalid Choice... :(");
				break;
			}
		}
		System.out.println("\nThank you for your support. We wish you a wonderful day ahead! :)");
	}
}
