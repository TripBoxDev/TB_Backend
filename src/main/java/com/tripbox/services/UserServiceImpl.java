package com.tripbox.services;

import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.User;
import com.tripbox.others.IdGenerator;
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
		
		//si el user es nuevo le asignamos una id
		if(user.getId()==null){
			
			String newId = IdGenerator.generateId();
			user.setId(newId);
		}
		
		try{
			//insertamos el user a la bbdd, no hace falta comprobar si existe, esto lo hace la msma bbdd
			bbdd.putUser(user);
			
			//devolvemos el elemento User, no hace falta hacer un Get a la bbdd
			return user;
		}catch (Exception e){
			throw new Exception();
		}
		
	}


	public void deleteUser(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
