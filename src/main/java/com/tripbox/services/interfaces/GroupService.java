package com.tripbox.services.interfaces;



import com.tripbox.elements.Group;

public interface GroupService {
	
	public Group getGroup(String id) throws Exception;
	public Group putGroup(Group user) throws Exception;
	public void deleteGroup(String id) throws Exception;
	
	/**
	 * Función que permite eliminar un User de un Group
	 * @param groupId
	 * @param userId
	 * @throws Exception
	 */
	public void deleteUserToGroup(String groupId, String userId) throws Exception;
	

}
