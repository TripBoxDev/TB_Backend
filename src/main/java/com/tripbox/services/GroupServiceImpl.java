package com.tripbox.services;

import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Group;
import com.tripbox.services.interfaces.GroupService;

public class GroupServiceImpl implements GroupService{
	
	Querys bbdd = Mock.getInstance();
	
	public GroupServiceImpl(){}

	public Group getGroup(String id) throws Exception {
		try{
			return bbdd.getGroup(id);
		}catch (Exception e){
			throw new Exception();
		}
	}

	
	public Group putUser(Group group) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void deleteGroup(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
