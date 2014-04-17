package com.tripbox.bbdd;


import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.tripbox.elements.User;

public class MongoDB {
	
	MongoCollection users;
	DB db;
	Jongo jongo;
	public MongoDB () throws UnknownHostException{
		db = new MongoClient().getDB("tripbox");

		jongo = new Jongo(db);
		users = jongo.getCollection("users");
	
	}
	
	public User getUser(String id) throws Exception {
		
		
		User user;
		 
		user = users.findOne(new ObjectId(id)).as(User.class);
		
		return user;
		
	}
	
	public void putUser(User user) throws Exception {
		try{
			//sobreescribimos si ya existe
			
		}catch ( Exception e){
			throw new Exception();
		}
		
	}
}
