package com.cust.hrms.dao;

import java.text.*;

public class MoneyFormatDao {
	
	public String moneyFormatPattern(double value, int places) {
	    DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
	    DecimalFormat df3 = new DecimalFormat("#,###,###,##0.000");
	    if (places == 2)
	        return df2.format(value);
	    else if (places == 3)
	        return df3.format(value);
	    else
	        throw new IllegalArgumentException();
	}
	
	public static void main(String args[]) {
		MoneyFormatDao mfd = new MoneyFormatDao();
		double money = 2345667.60;
		String format = mfd.moneyFormatPattern(money, 2);
		System.out.println(format);
	}

}
