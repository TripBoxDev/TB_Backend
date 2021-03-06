package com.tripbox.services;

import org.bson.types.ObjectId;

import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.MongoDB;
import com.tripbox.bbdd.exceptions.ItemNotFoundException;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.others.IdGenerator;
import com.tripbox.services.exceptions.ElementNotFoundServiceException;
import com.tripbox.services.exceptions.IdAlreadyExistException;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.RequiredParametersException;
import com.tripbox.services.interfaces.GroupService;
import com.tripbox.services.interfaces.UserService;

public class UserServiceImpl implements UserService {

	// Querys bbdd = Mock.getInstance();
	IdGenerator idGen = IdGenerator.getInstance();

	MongoDB mongo;

	public UserServiceImpl() {
	}

	public User getUser(String id) throws Exception {
		try {
			mongo = MongoDB.getInstance();

			return mongo.getUser(id);
		} catch (Exception e) {
			throw new Exception();
		}
	}

	public User putUser(User user) throws Exception {
		mongo = MongoDB.getInstance();
		if (user.getName() == null || user.getName().equalsIgnoreCase("")) {
			throw new RequiredParametersException(
					"The paramater name is required");
		}
		// nos llega un User sin id
		if (user.getId() == null) {

			if (user.getEmail() != null) {
				try {
					user = mongo.getUserbyEmail(user.getEmail());
				} catch (ItemNotFoundException e) {

					user = putNewUser(user);
				}

			} else if (user.getGoogleId() != null) {
				try {
					user = mongo.getUserbyGoogleId(user.getGoogleId());
				} catch (ItemNotFoundException e) {

					user = putNewUser(user);
				}
			} else if (user.getFacebookId() != null) {
				try {
					user = mongo.getUserbyFacebookId(user.getFacebookId());
				} catch (ItemNotFoundException e) {

					user = putNewUser(user);
				}
			} else {
				throw new InvalidIdsException("Ning�n identificador definido");
			}

		} else {

			try {
				// comprobamos que el id existe
				mongo.getUser(user.getId());
				// modificamos el User en la bbdd
				mongo.UpdateUser(user);
			} catch (Exception exc) {
				throw new InvalidIdsException("El usuario con el ID, "
						+ user.getId() + ", no exsiste");
			}

		}
		// devolvemos el elemento User completo
		return user;

	}

	private User putNewUser(User user) throws Exception {
		String newId = idGen.generateId();
		user.setId(newId);
		while (true) {
			try {
				// comprovamos si el id existe
				try {
					this.getUser(newId);
					// generamos nueva id
					throw new IdAlreadyExistException();
				} catch (IdAlreadyExistException ex) {
					throw new IdAlreadyExistException();
				} catch (Exception e) {
					// insertamos el user a la bbdd
					// bbdd.putUser(user);
					mongo = MongoDB.getInstance();
					mongo.putUser(user);
					break;
				}

			} catch (IdAlreadyExistException ex) {
				// si el id ya existe probamos con otro id
				newId = idGen.generateId();
				user.setId(newId);
				continue;
			}
		}
		return user;
	}

	public void deleteUser(String id) throws Exception {

		mongo = MongoDB.getInstance();

		GroupService groupService = new GroupServiceImpl();
		try {
			User user = this.getUser(id);
			for (String groupId : user.getGroups()) {
				groupService.deleteUserToGroup(groupId, id);
			}
			mongo.deleteUser(id);
		} catch (Exception e) {
			throw new Exception();
		}

	}

	public void addGroupToUser(String userId, String groupId) throws Exception {
		if ((userId != null) && (groupId != null)) {

			User user = null;
			Group group = null;
			GroupService groupService = new GroupServiceImpl();
			try {
				user = this.getUser(userId);
			} catch (Exception e) {
				throw new ElementNotFoundServiceException(
						"El usuario con el ID, " + userId + ", no exsiste");
			}

			try {
				group = groupService.getGroup(groupId);
			} catch (Exception e) {
				throw new ElementNotFoundServiceException(
						"El grupo con el ID, " + groupId + ", no exsiste");
			}

			if (!user.getGroups().contains(group.getId())) {
				user.getGroups().add(group.getId());
			}
			if (!group.getUsers().contains(user.getId())) {
				group.getUsers().add(user.getId());
			}

			this.putUser(user);
			groupService.putGroup(group);

		} else {
			throw new InvalidIdsException(
					"La ID del grupo o del usuario son nulas");
		}
	}
}