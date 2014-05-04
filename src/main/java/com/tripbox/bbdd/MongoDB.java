package com.tripbox.bbdd;


import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.Oid;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.tripbox.bbdd.exceptions.ItemNotFoundException;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.others.IdGenerator;

public class MongoDB implements Querys{
	
	private static MongoCollection users;
	private static MongoCollection groups;
	private static DB db;
	private static Jongo jongo;
	private static MongoDB uniqueInstance;
	
	
	private MongoDB(){}
	public static MongoDB getInstance() throws UnknownHostException{
		if(uniqueInstance == null){
			uniqueInstance=new MongoDB();
			db = new MongoClient().getDB("tripbox");

			jongo = new Jongo(db);
			users = jongo.getCollection("users");
			groups = jongo.getCollection("groups");

		}
		return uniqueInstance;
	}
	
	
	public User getUser(String id) throws Exception {
		
		
		User user;
		 
		user = users.findOne("{_id : '"+id+"'}").as(User.class);
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
			
			users.update("{_id : '"+user.getId()+"'}").with(user);
			
		}catch ( Exception e){
			e.printStackTrace();
			throw new Exception();
		}
		
	}
	
	public void deleteUser(String id) throws Exception {
		
		User user;
		
		user = users.findOne("{_id : '"+id+"'}").as(User.class);
		
		if (user != null){
			users.remove("{_id : '"+id+"'}");
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
		 
		group = groups.findOne("{_id : '"+id+"'}").as(Group.class);
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
				this.getGroup(group.getId()) ;
				groups.update("{_id : '"+group.getId()+"'}").with(group);
				
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
			groups.remove("{_id : '"+id+"'}");
		}else {
			throw new Exception();
		}
	}
}
