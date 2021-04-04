package com.project.userTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.user.IUser;

public class UserMock {
	public IUser createUserMock(IUser user) {
		user.setId(5);
		user.setUserName("dhara@gmail.com");
		user.setFirstName("Dhara");
		user.setLastName("Gohil");
		user.setGender("Female");
		
		String dateStr = "2000-04-06";
		try {
			Date date = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
			user.setDateOfBirth(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		user.setEnabled(true);
		user.setMobileNumber("1234567890");
		user.setPassword("Dhara");
		user.setRole("USER");
		
		return user;
	}

}
