package com.cust.hrms.statues;

public class LeaveStatues {
	private String name;
	
	public String getStatusName(String status) {
		
		if(status.trim().toLowerCase().equals("approved")) {
			name = "Approve";
		}
		else if(status.trim().toLowerCase().equals("approved recalled back")) {
			name = "Approve Recall Back";
		}
		else if(status.trim().toLowerCase().equals("declined")) {
			name = "Decline";
		}
		else if(status.trim().toLowerCase().equals("drafted")) {
			name = "Draft";
		}
		else if(status.trim().toLowerCase().equals("sent back for correction")) {
			name = "Send Back For Correction";
		}
		else if(status.trim().toLowerCase().equals("sent to hr for approval")) {
			name = "Send To HR For Approval";
		}
		else if(status.trim().toLowerCase().equals("sent to supervisor for approval")) {
			name = "Send To Supervisor For Approval";
		}
		return name;
	}
}
