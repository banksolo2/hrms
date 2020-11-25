package com.cust.hrms.notification;

public class LevelPayElementNotification {
	private String message;
	
	public String getCreateLevelPayElementMessage(boolean result) {
		if(result) {
			message = "Level pay element has been created.....";
		}
		else {
			message = "Enable to create level pay element......";
		}
		return message;
	}
	
	public String getUpdateLevelPayElementMessage(boolean result) {
		if(result) {
			message = "Level pay element has been updated......";
		}
		else {
			message = "Enable to update level pay element......";
		}
		return message;
	}
	
	public String getDeleteLevelPayElementMessage(boolean result) {
		if(result) {
			message = "Level pay element has been deleted......";
		}
		else {
			message = "Enable to delete level pay element.......";
		}
		return message;
	}
	
	public String getLevelPayElementAlreadyExistErrorMessage() {
		return "Level pay element already exist.....";
	}
	
	
}
