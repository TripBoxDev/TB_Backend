package com.tripbox.services;

import java.util.ArrayList;

import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.MongoDB;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.others.IdGenerator;
import com.tripbox.services.exceptions.UserNotExistOnGroup;
import com.tripbox.services.interfaces.GroupService;
import com.tripbox.services.interfaces.UserService;

public class GroupServiceImpl implements GroupService {



	public GroupServiceImpl() {
	}

	public Group getGroup(String id) throws Exception {
		MongoDB mongo = new MongoDB();
		try {
			return mongo.getGroup(id);
		} catch (Exception e) {
			throw new ElementNotFoundException("El grup no s'ha trobat.");
		}
	}

	public Group putGroup(Group group) throws Exception {
		MongoDB mongo = new MongoDB();
		// si el user es nuevo le asignamos una id
		

		try {
			// insertamos el grupo a la bbdd, no hace falta comprobar si existe,
			// esto lo hace la misma bbdd
			group =mongo.putGroup(group);

			// devolvemos el elemento Grupo, no hace falta hacer un Get a la
			// bbdd
			return group;
		} catch (Exception e) {
			throw new Exception();
		}

	}

	public void deleteGroup(String id) throws Exception {
		MongoDB mongo = new MongoDB();
		try {
			mongo.deleteGroup(id);
		} catch (Exception e) {
			throw new Exception();
		}

	}

	public void deleteUserToGroup(String groupId, String userId)
			throws Exception {
		
		UserService userService = new UserServiceImpl();
		try {
			this.getGroup(groupId);
			System.out.println("hola1");
		} catch (Exception e) {
			throw new Exception(groupId.toString());
		}

		try {
			userService.getUser(userId);
			System.out.println("hola2");
		} catch (Exception e) {
			throw new Exception(userId.toString());
		}
		System.out.println("hola3");
		// eliminamos el user de la lista de users del grupo
		Group group = this.getGroup(groupId);
		ArrayList<String> groupUsers = group.getUsers();
		if (!groupUsers.remove(userId)) {
			System.out.println("hola4");
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
			System.out.println("hola3");
		} else {
			this.putGroup(group);
		}

		userService.putUser(user);
	}

}