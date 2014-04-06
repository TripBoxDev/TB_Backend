package com.tripbox.services;

import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.exceptions.ItemNotFoundException;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.User;
import com.tripbox.others.IdGenerator;
import com.tripbox.services.Exceptions.InvalidIdsException;
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
		
		//nos llega un User sin id
		if(user.getId()==null){
			if(user.getEmail()!=null){
				try{
					user = bbdd.getUserbyEmail(user.getEmail());
				}catch (ItemNotFoundException e){
					
					user = putNewUser(user);
				}
				
			}else if(user.getGoogleId()!=null){
				try{
					user = bbdd.getUserbyGoogleId(user.getGoogleId());
				}catch (ItemNotFoundException e){
					user = putNewUser(user);
				}
			}else if(user.getFacebookId()!=null){
				try{
					user = bbdd.getUserbyFacebookId(user.getFacebookId());
				}catch (ItemNotFoundException e){
					user = putNewUser(user);
				}
			}else{
				throw new InvalidIdsException("Ningún identificador definido");
			}
			
		}else{
			
			try{
				//comprobamos que el id existe
				bbdd.getUser(user.getId());
				//modificamos el User en la bbdd
				bbdd.putUser(user);
			}catch (Exception exc){
				throw new InvalidIdsException("El usuario con el ID, "+user.getId()+", no exsiste");
			}
			
		}
		//devolvemos el elemento User completo
		return user;
		
		
	}

	private User putNewUser(User user) throws Exception{
		String newId = IdGenerator.generateId();
		user.setId(newId);
		while(true){
			try{
				//comprovamos si el id existe
				try{
					bbdd.getUser(newId);
					//generamos nueva id
					throw new Exception();
				}catch (Exception e){
					//insertamos el user a la bbdd
					bbdd.putUser(user);
					
					break;
				}
				
				
				
			} catch(Exception ex){
				//si el id ya existe probamos con otro id
				newId = IdGenerator.generateId();
				user.setId(newId);
				continue;
			}
		}
		return user;
	}
	public void deleteUser(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
