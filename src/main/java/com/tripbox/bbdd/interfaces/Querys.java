package com.tripbox.bbdd.interfaces;

import com.tripbox.elements.User;

public interface Querys {
	
	public User getUser(String id) throws Exception;
	public void putUser(User user) throws Exception;

}
