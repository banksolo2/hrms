package com.cust.hrms.statues;

public class RequisitionStatues {
	private String name;
	
	public String getStatusName(String status) {
		if(status.trim().toLowerCase().equals("drafted")) {
			name = "Draft";
		}
		else if(status.trim().toLowerCase().equals("sent to supervisor for authorization")) {
			name = "Send To Supervisor For Authorization";
		}
		else if(status.trim().toLowerCase().equals("sent to recipient for approval")) {
			name = "Send To Recipient For Approval";
		}
		else if(status.trim().toLowerCase().equals("approved")) {
			name = "Approve";
		}
		else if(status.trim().toLowerCase().equals("closed")) {
			name = "Close";
		}
		else if(status.trim().toLowerCase().equals("declined")) {
			name = "Decline";
		}
		else {
			name = "No Such Status";
		}
		return name;
	}
}
