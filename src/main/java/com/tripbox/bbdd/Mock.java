package com.tripbox.bbdd;

import java.util.ArrayList;
import java.util.HashMap;

import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;

public class Mock implements Querys {


	
	private static Mock uniqueInstance;
	public HashMap<String,User> mock = new HashMap<String,User>();
	public HashMap<String, Group> mockGroup = new HashMap<String,Group>();
	
	public static Mock getInstance(){
		if(uniqueInstance == null){
			uniqueInstance=new Mock();
			uniqueInstance.firstUser();
			uniqueInstance.firstGroup();

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
	
	private void firstGroup(){
		ArrayList<String> users = new ArrayList<String>();
		users.add("123456");
		users.add("165432");
		Group first = new Group("445566","Backend", "Els millors", users );
		mockGroup.put("445566", first);
	}
	

	public User getUser(String id) throws Exception {
		if(mock.get(id)!=null){
					
			return mock.get(id);

		}else {
			throw new Exception();
		}
	}

	public void putUser(User user) throws Exception {
		try{
			//sobreescribimos si ya existe
			mock.put(user.getId(), user);
		}catch ( Exception e){
			throw new Exception();
		}
		
	}

	@Override
	public Group getGroup(String id) throws Exception {
		if(mockGroup.get(id)!=null){
			
			return mockGroup.get(id);

		}else {
			throw new Exception();
		}
	}

	@Override
	public void putGroup(Group group) throws Exception {
		try{
			//sobreescribimos si ya existe
			mockGroup.put(group.getId(), group);
		}catch ( Exception e){
			throw new Exception();
		}
	}
	
	

}
