package com.tripbox.services;

import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.exceptions.ItemNotFoundException;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.others.IdGenerator;
import com.tripbox.services.exceptions.IdAlreadyExistException;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.RequiredParametersException;
import com.tripbox.services.interfaces.GroupService;
import com.tripbox.services.interfaces.UserService;

public class UserServiceImpl implements UserService {
	
	Querys bbdd = Mock.getInstance();
	IdGenerator idGen=IdGenerator.getInstance();
	
	public UserServiceImpl(){}

	public User getUser(String id) throws Exception {
		try{
			return bbdd.getUser(id);
		}catch (Exception e){
			throw new Exception();
		}
	}


	public User putUser(User user) throws Exception {
		if(user.getName()==null || user.getName().equalsIgnoreCase("")){
			throw new RequiredParametersException("The paramater name is required");
		}
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
				throw new InvalidIdsException("Ning������n identificador definido");
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
		String newId = idGen.generateId();
		user.setId(newId);
		while(true){
			try{
				//comprovamos si el id existe
				try{
					bbdd.getUser(newId);
					//generamos nueva id
					throw new IdAlreadyExistException();
				}catch (IdAlreadyExistException ex){
					throw new IdAlreadyExistException();
				}catch (Exception e){
					//insertamos el user a la bbdd
					bbdd.putUser(user);
					
					break;
				}
				
				
				
			} catch(IdAlreadyExistException ex){
				//si el id ya existe probamos con otro id
				newId = idGen.generateId();
				user.setId(newId);
				continue;
			}
		}
		return user;
	}
	
	public void deleteUser(String id) throws Exception {
		try{
			bbdd.deleteUser(id);
		}catch (Exception e){
			throw new Exception();
		}
		
	}


	public void addGroupToUser(String userId, String groupId) throws Exception {
		if ((userId!=null)&&(groupId!=null)){
			
			User user=null;
			Group group=null;
			GroupService  groupService = new GroupServiceImpl();
			try {
				user = bbdd.getUser(userId);
			} catch (Exception e) {
				throw new ElementNotFoundException("El usuario con el ID, "+ userId +", no exsiste");
			}
			
			try {
				group = bbdd.getGroup(groupId);
			} catch (Exception e) {
				throw new ElementNotFoundException("El grupo con el ID, "+ groupId +", no exsiste");
			}
			
	
		
			user.getGroups().add(group.getId());
			group.getUsers().add(user.getId());
			this.putUser(user);
			groupService.putGroup(group);
				
				
			
		} else {
			throw new InvalidIdsException("La ID del grupo o del usuario son nulas");
		}
	}

}
