package com.arc.cardemo.utils;
import com.arc.cardemo.domain.DbHelper;
import com.arc.cardemo.domain.User;
import com.arc.cardemo.utils.LoggingHelper;

public class DbTester {
	
	private User user = new User();
	private static DbHelper dbLookup = new DbHelper();

	public static void main(String[] args) {
		System.out.println("trying to find db lookup ");
		dbLookup.doLookup("Admin");
		

	}

}
