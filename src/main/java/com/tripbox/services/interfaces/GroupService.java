package com.tripbox.services.interfaces;



import com.tripbox.elements.Group;

public interface GroupService {
	
	public Group getGroup(String id) throws Exception;
	public Group putGroup(Group user) throws Exception;
	public void deleteGroup(String id) throws Exception;
	

}
