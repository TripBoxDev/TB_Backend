package com.tripbox.bbdd;

import java.util.ArrayList;
import java.util.HashMap;

import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.User;

public class Mock implements Querys {


	
	private static Mock uniqueInstance;
	public HashMap<String,User> mock = new HashMap<String,User>();
	
	public static Mock getInstance(){
		if(uniqueInstance == null){
			uniqueInstance=new Mock();
			uniqueInstance.firstUser();

		}
		return uniqueInstance;
	}
	
	private void firstUser(){
		ArrayList<String> groups = new ArrayList<String>();
		groups.add("445566");
		groups.add("98765");
		User first = new User("123456","Pepitu", "Sigaler", "psigaler@gmail.com",groups );
		mock.put("123456", first);
	}
	
	@Override
	public User getUser(String id) throws Exception {
		if(mock.get(id)!=null){
					
			return mock.get(id);

		}else {
			throw new Exception();
		}
	}

}
