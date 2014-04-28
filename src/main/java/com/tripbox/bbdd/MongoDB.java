package com.tripbox.bbdd;


import java.net.UnknownHostException;
import java.util.Iterator;

import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.tripbox.bbdd.exceptions.ItemNotFoundException;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;

public class MongoDB implements Querys{
	
	MongoCollection users;
	MongoCollection groups;
	DB db;
	Jongo jongo;
	public MongoDB () throws UnknownHostException{
		db = new MongoClient().getDB("tripbox");

		jongo = new Jongo(db);
		users = jongo.getCollection("users");
		groups = jongo.getCollection("groups");
	
	}
	
	public User getUser(String id) throws Exception {
		
		
		User user;
		 
		user = users.findOne(new ObjectId(id)).as(User.class);
		if (user != null){
		return user;
		}
		else {
			throw new Exception();
		}
	}
	
	public void putUser(User user) throws Exception {
		
		try{
			
			
			 users.insert(user);
			 
			 
			
		}catch ( Exception e){
			e.printStackTrace();
			throw new Exception();
		}
		
	}
	
	public void UpdateUser(User user) throws Exception {
		try{
			
			users.update(new ObjectId(user.getId())).with(user);
			
		}catch ( Exception e){
			e.printStackTrace();
			throw new Exception();
		}
		
	}
	
	public void deleteUser(String id) throws Exception {
		
		User user;
		
		user = users.findOne(new ObjectId(id)).as(User.class);
		
		if (user != null){
			users.remove(new ObjectId(id));
		}
		else {
			throw new Exception();
		}
	}


	public User getUserbyFacebookId(String facebookId) throws ItemNotFoundException {
		
		User user = users.findOne("{facebookId: #}",facebookId).as(User.class);
		if (user != null){
					return user;
		}
		throw new ItemNotFoundException("El item con facebookId: "+facebookId+" no existe en la bbdd");
		
	}


	public User getUserbyGoogleId(String googleId) throws Exception {
		User user = users.findOne("{googleId:  #}",googleId).as(User.class);
		if (user != null){
					return user;
		}
		throw new ItemNotFoundException("El item con googleId: "+googleId+" no existe en la bbdd");
	}

	public User getUserbyEmail(String email) throws Exception {
		User user = users.findOne("{email: #}",email).as(User.class);
		if (user != null){
					return user;
		}
		
		throw new ItemNotFoundException("El item con el email: "+email+" no existe en la bbdd");
	}
	
	public Group getGroup(String id) throws Exception {
		Group group;
		 
		group = groups.findOne(new ObjectId(id)).as(Group.class);
		if (group != null){
		return group;
		}
		else {
			throw new Exception();
		}
	}
	
	public void putGroup(Group group) throws Exception {
		try{
			try{
			getGroup(group.getId()) ;
				groups.update(new ObjectId(group.getId())).with(group);
				
			}
			catch ( Exception e){
				
				groups.save(group);
				
			}
		
			
		}catch ( Exception e){
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	public void deleteGroup(String id) throws Exception {
		if(getGroup(id)!=null){
			groups.remove(new ObjectId(id));
		}else {
			throw new Exception();
		}
	}
}
