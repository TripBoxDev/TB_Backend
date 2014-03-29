package com.tripbox.services;

import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Group;
import com.tripbox.others.IdGenerator;
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

	
	public Group putGroup(Group group) throws Exception {
		//si el user es nuevo le asignamos una id
		if(group.getId()==null){
					
			String newId = IdGenerator.generateId();
			group.setId(newId);
		}
				
		try{
			//insertamos el grupo a la bbdd, no hace falta comprobar si existe, esto lo hace la misma bbdd
			bbdd.putGroup(group);
					
			//devolvemos el elemento Grupo, no hace falta hacer un Get a la bbdd
			return group;
		}catch (Exception e){
			throw new Exception();
		}
				
	}

	
	public void deleteGroup(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}



}
