package com.tripbox.services.interfaces;



import com.tripbox.elements.User;

public interface UserService {

	public User getUser(String id) throws Exception;
	public User putUser(User user) throws Exception;
	public void deleteUser(String id)throws Exception;
}
