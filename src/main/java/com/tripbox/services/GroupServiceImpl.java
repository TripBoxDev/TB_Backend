package com.tripbox.services;

import java.util.ArrayList;

import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.others.IdGenerator;
import com.tripbox.services.exceptions.UserNotExistOnGroup;
import com.tripbox.services.interfaces.GroupService;
import com.tripbox.services.interfaces.UserService;

public class GroupServiceImpl implements GroupService {

	Querys bbdd = Mock.getInstance();

	public GroupServiceImpl() {
	}

	public Group getGroup(String id) throws Exception {
		try {
			return bbdd.getGroup(id);
		} catch (Exception e) {
			throw new ElementNotFoundException("El grup no s'ha trobat.");
		}
	}

	public Group putGroup(Group group) throws Exception {
		// si el user es nuevo le asignamos una id
		if (group.getId() == null) {

			String newId = IdGenerator.generateId();
			group.setId(newId);
		}

		try {
			// insertamos el grupo a la bbdd, no hace falta comprobar si existe,
			// esto lo hace la misma bbdd
			bbdd.putGroup(group);

			// devolvemos el elemento Grupo, no hace falta hacer un Get a la
			// bbdd
			return group;
		} catch (Exception e) {
			throw new Exception();
		}

	}

	public void deleteGroup(String id) throws Exception {
		try {
			bbdd.deleteGroup(id);
		} catch (Exception e) {
			throw new Exception();
		}

	}

	public void deleteUserToGroup(String groupId, String userId)
			throws Exception {
		UserService userService = new UserServiceImpl();
		try {
			this.getGroup(groupId);
		} catch (Exception e) {
			throw new Exception(groupId.toString());
		}

		try {
			userService.getUser(userId);
		} catch (Exception e) {
			throw new Exception(userId.toString());
		}

		// eliminamos el user de la lista de users del grupo
		Group group = this.getGroup(groupId);
		ArrayList<String> groupUsers = group.getUsers();
		if (!groupUsers.remove(userId)) {
			throw new UserNotExistOnGroup(userId);
		}
		group.setUsers(groupUsers);
		// eliminamos el grup de la lista de grupos del usuario
		User user = userService.getUser(userId);
		ArrayList<String> userGroups = user.getGroups();
		userGroups.remove(groupId);
		user.setGroups(userGroups);

		// actualizamos la bbdd
		if (group.getUsers().isEmpty()) {
			this.deleteGroup(groupId);
		} else {
			this.putGroup(group);
		}

		userService.putUser(user);
	}

}