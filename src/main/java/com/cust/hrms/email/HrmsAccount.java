package com.cust.hrms.email;

public class HrmsAccount {
	private final String EMAIL_ADDRESS = "HrmsBanksTech@gmail.com";
	private final String PASSWORD = "Olotu1234_";
	
	public String getUserName() {
		return EMAIL_ADDRESS;
	}
	
	public String getPassword() {
		return PASSWORD;
	}
	
	
	public static void main(String args[]) {
		HrmsAccount ha = new HrmsAccount();
		System.out.println(ha.getPassword());
	}
}
