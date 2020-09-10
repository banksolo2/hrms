package com.cust.hrms.dao;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateDao {
	
	public String convertDateFormat(String date, String splitBy) {
		String arr[] = date.split(splitBy);
		
		return arr[2]+"-"+arr[0]+"-"+arr[1];
	}
	
	public int getNoOfDaysFromTwoDate(String startDate, String endDate) {
		int result = 0;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		    Date date1 = df.parse(startDate);
		    Date date2 = df.parse(endDate);
		    Calendar cal1 = Calendar.getInstance();
		    Calendar cal2 = Calendar.getInstance();
		    cal1.setTime(date1);
		    cal2.setTime(date2);
	
		    
		    while (cal1.before(cal2)) {
		        if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
		           &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
		            result++;
		        }
		        cal1.add(Calendar.DATE,1);
		    }
		}
		catch(Exception ex) {
			System.out.println(ex.fillInStackTrace());
		}
	    return result+1;
	}
	
	public boolean compareDate(String startDate, String endDate) {
		boolean result = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date firstDate = sdf.parse(startDate);
			Date secondDate = sdf.parse(endDate);
			//System.out.println("First Date: "+firstDate);
			if(firstDate.after(secondDate)) {
				result = false;
			}
			else if(firstDate.before(secondDate)) {
				result = true;
			}
			else if(firstDate.equals(secondDate)) {
				result = true;
			}
			
		}
		catch(Exception ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public boolean isStartDateValid(String startDate) {
		boolean result = false;
		try {
			startDate += " 23:59:00";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = new Date();
			Date date = sdf.parse(startDate);
			System.out.println(date);
			
			if(today.after(date)) {
				result = false;
			}
			else if(today.equals(date)) {
				result = true;
			}
			else if(today.before(date)) {
				result = true;
			}
		}
		catch(Exception ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return result;
	}
	
	public String joinStartDateAndEndDate(String startDate, String endDate) {
		String dateBegin[] = startDate.split("-");
		String dates = dateBegin[1]+"/"+dateBegin[2]+"/"+dateBegin[0];
		dates += " - ";
		String dateEnd[] = endDate.split("-");
		dates += dateEnd[1]+"/"+dateEnd[2]+"/"+dateEnd[0];
		return dates;
	}
	
	public String  joinStartDateAndEndDateToWord(String startDate, String endDate) {
		String dates = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date dateFrom = sdf.parse(startDate);
			dates = dateFrom.toString();
		}
		catch(Exception ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return dates;
	}
	
	public static void main(String args[]) {
		DateDao dd = new DateDao();
		String result = dd.joinStartDateAndEndDateToWord("2020-06-21", "2020-06-29");
		System.out.println(result);
	}
}
