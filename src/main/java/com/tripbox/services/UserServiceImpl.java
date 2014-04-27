package com.tripbox.services;

import org.bson.types.ObjectId;

import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.MongoDB;
import com.tripbox.bbdd.exceptions.ItemNotFoundException;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.User;
import com.tripbox.others.IdGenerator;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.RequiredParametersException;
import com.tripbox.services.interfaces.UserService;

public class UserServiceImpl implements UserService {
	
	
	
	public UserServiceImpl(){}

	public User getUser(String id) throws Exception {
		try{
			MongoDB mongo = new MongoDB();
			
			return mongo.getUser(id);
		}catch (Exception e){
			throw new Exception();
		}
	}


	public User putUser(User user) throws Exception {
		MongoDB mongo = new MongoDB();
		if(user.getName()==null || user.getName().equalsIgnoreCase("")){
			throw new RequiredParametersException("The paramater name is required");
		}
		//nos llega un User sin id
		if(user.getId()==null){
			System.out.println("hola1");
			if(user.getEmail()!=null){
				try{
					user = mongo.getUserbyEmail(user.getEmail());
				}catch (ItemNotFoundException e){
					
					return user = mongo.putUser(user);
				}
				
			}else if(user.getGoogleId()!=null){
				try{
					user = mongo.getUserbyGoogleId(user.getGoogleId());
				}catch (ItemNotFoundException e){
					
					return user = mongo.putUser(user);
				}
			}else if(user.getFacebookId()!=null){
				try{
					user = mongo.getUserbyFacebookId(user.getFacebookId());
				}catch (ItemNotFoundException e){
					
					return user = mongo.putUser(user);
				}
			}else{
				throw new InvalidIdsException("Ning��n identificador definido");
			}
			
		}else{
			
			try{
				//comprobamos que el id existe
				mongo.getUser(user.getId());
				//modificamos el User en la bbdd
				mongo.UpdateUser(user);
			}catch (Exception exc){
				throw new InvalidIdsException("El usuario con el ID, "+user.getId()+", no exsiste");
			}
			
		}
		//devolvemos el elemento User completo
		return user;
		
		
	}

	
	public void deleteUser(String id) throws Exception {
		MongoDB mongo = new MongoDB();
		try{
			mongo.deleteUser(id);
		}catch (Exception e){
			throw new Exception();
		}
		
	}

}
