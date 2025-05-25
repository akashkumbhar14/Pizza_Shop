package com.dkte.pizzashop.entities;

import java.util.Scanner;

public class Order {
	private int oid;
	private int cid;
	private int mid;

	public Order() {
	}

	public Order(int oid, int cid, int mid) {
		super();
		this.oid = oid;
		this.cid = cid;
		this.mid = mid;
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

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public void accept(Scanner sc) {
		// user can give menu id
	}

	@Override
	public String toString() {
		return "Order [oid=" + oid + ", cid=" + cid + ", mid=" + mid + "]";
	}
}
