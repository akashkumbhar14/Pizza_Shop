package com.dkte.pizzashop.entities;

public class OrderDetalis {
	private int oid;
	private int cid;
	private String cName;
	private String mName;
	private double price;

	public OrderDetalis() {
	}

	public OrderDetalis(int oid, int cid, String cName, String mNname, double price) {
		super();
		this.oid = oid;
		this.cid = cid;
		this.cName = cName;
		this.mName = mNname;
		this.price = price;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getmNname() {
		return mName;
	}

	public void setmNname(String mNname) {
		this.mName = mNname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		System.out.println("Order ID: " + oid);
		System.out.println("Customer ID: " + cid);
		System.out.println("Name: " + cName);
		System.out.println("Price: " + price);
		System.out.println("Order name: " + mName);

		return "";
	}

}
