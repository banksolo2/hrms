package com.cust.hrms.statues;

public class SupportTicketStatues {
	private String name;
	
	public String getStatusName(String status) {
		if(status.trim().toLowerCase().equals("resolved")) {
			name = "Resolve";
		}
		else if(status.trim().toLowerCase().equals("closed")) {
			name = "Close";
		}
		else if(status.trim().toLowerCase().equals("unresolved")) {
			name = "Unresolve";
		}
		else {
			name = status.trim();
		}
		return name;
	}
}
