package com.cust.hrms.models;

public class Month {
	private String no;
	private String name;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Month [no=" + no + ", name=" + name + "]";
	}
	
	
}
