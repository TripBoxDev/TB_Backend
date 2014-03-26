package com.tripbox.services;

import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.User;
import com.tripbox.services.interfaces.UserService;

public class UserServiceImpl implements UserService {
	
	Querys bbdd = Mock.getInstance();
	
	public UserServiceImpl(){}

	
	public User getUser(String id) throws Exception {
		try{
			return bbdd.getUser(id);
		}catch (Exception e){
			throw new Exception();
		}
	}

	
	public User putUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void deleteUser(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
