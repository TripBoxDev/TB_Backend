package com.tripbox.bbdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.tripbox.bbdd.exceptions.ItemNotFoundException;
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
			uniqueInstance.secondUser();
			uniqueInstance.firstGroup();
			uniqueInstance.secondGroup();
			uniqueInstance.thirdGroup();
			uniqueInstance.user0();

		}
		return uniqueInstance;
	}
	
	private void firstUser(){
		ArrayList<String> groups = new ArrayList<String>();
		groups.add("445566");
		groups.add("98765");
		User first = new User("123456", "f123456", "g123456", "Pepitu", "Sigaler", "psigaler@gmail.com",groups);
		mock.put("123456", first);
	}
	
	private void secondUser(){
		ArrayList<String> groups = new ArrayList<String>();
		groups.add("445566");
		groups.add("98765");
		User second = new User("165432", null, null, "Marc", "Sigaler", "msigaler@gmail.com",groups );
		mock.put("165432", second);
	}
	
	private void firstGroup(){
		ArrayList<String> users = new ArrayList<String>();
		users.add("123456");
		users.add("165432");
		Group first = new Group("445566","Backend", "Els millors", users );
		mockGroup.put("445566", first);
	}
	
	private void secondGroup(){
		ArrayList<String> users = new ArrayList<String>();
		users.add("654321");
		users.add("234561");
		Group second = new Group("665544","Frontent", "No ho fan malament xD", users );
		mockGroup.put("665544", second);
	}
	
	private void thirdGroup(){
		ArrayList<String> users = new ArrayList<String>();
		users.add("987654");
		users.add("454361");
		Group third = new Group("331188","Requisits", "Chupatintas", users );
		mockGroup.put("331188", third);
	}
	
	private void user0(){
		ArrayList<String> groups = new ArrayList<String>();
		groups.add("445566");
		groups.add("98765");
		User usr = new User("0", null, null, "Pepet", null, null, null );
		mock.put("0", usr);
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
	
	public void deleteUser(String id) throws Exception {
		if(mock.get(id)!=null){
			mock.remove(id);
		}else {
			throw new Exception();
		}
	}

	public Group getGroup(String id) throws Exception {
		if(mockGroup.get(id)!=null){
			
			return mockGroup.get(id);

		}else {
			throw new Exception();
		}
	}


	public void putGroup(Group group) throws Exception {
		try{
			//sobreescribimos si ya existe
			mockGroup.put(group.getId(), group);
		}catch ( Exception e){
			throw new Exception();
		}
	}
	
	public void deleteGroup(String id) throws Exception {
		if(mockGroup.get(id)!=null){
			mockGroup.remove(id);
		}else {
			throw new Exception();
		}
		
	}


	public User getUserbyFacebookId(String facebookId) throws ItemNotFoundException {
		
		Iterator<User> it = mock.values().iterator();
		while (it.hasNext()) {
			User user = it.next();
			if(user.getFacebookId()!=null){
				if(user.getFacebookId().equals(facebookId)){
					return user;
				}
			}
		
		}
		
		throw new ItemNotFoundException("El item con facebookId: "+facebookId+" no existe en la bbdd");
	}


	public User getUserbyGoogleId(String googleId) throws Exception {
		Iterator<User> it = mock.values().iterator();
		while (it.hasNext()) {
			User user = it.next();
			if(user.getGoogleId() !=null){
				if(user.getGoogleId().equals(googleId)){
					return user;
				}
			}
		
		}
		
		throw new ItemNotFoundException("El item con googleId: "+googleId+" no existe en la bbdd");
	}

	public User getUserbyEmail(String email) throws Exception {
		Iterator<User> it = mock.values().iterator();
		while (it.hasNext()) {
			User user = it.next();
			if(user.getEmail()!=null){
				if(user.getEmail().equals(email)){
					return user;
				}
			}
		
		}
		
		throw new ItemNotFoundException("El item con el email: "+email+" no existe en la bbdd");
	}
	

}
