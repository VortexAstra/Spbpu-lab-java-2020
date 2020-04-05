package com.company.tableDataBase;

import com.company.controller.Controller;

public class TableData extends Controller {

	private int id;
	private String prodid;
	private String title;
	private int cost;

	public TableData(int id, String prodid, String title, int cost) {
		this.id = id;
		this.prodid = prodid;
		this.title = title;
		this.cost = cost;
	}

	public TableData(int id, int prodid, String title, int cost) {

	}

	public void setId(int id) {
		this.id = id;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getId() {
		return id;
	}

	public String getProdid() {
		return prodid;
	}

	public String getTitle() {
		return title;
	}

	public int getCost() {
		return cost;
	}


}