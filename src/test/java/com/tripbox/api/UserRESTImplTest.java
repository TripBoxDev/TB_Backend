package com.tripbox.api;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.codehaus.jackson.map.util.JSONPObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.services.GroupServiceImpl;
import com.tripbox.services.UserServiceImpl;
import com.tripbox.services.interfaces.UserService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UserRESTImplTest {
	static GroupServiceImpl grupoServ = new GroupServiceImpl();
	static UserService userService = new UserServiceImpl();
	static ArrayList<String> users = new ArrayList<String>();
	static ArrayList<String> groups = new ArrayList<String>();
	static Group group;
	static User usuario;

	@BeforeClass
	public static void SetUp(){
		group = new Group(null,"prueba1","nada", users);
		usuario = new User(null,"jo","ja","ji","gh", "lo", groups);
		
		//añadimos usuario y grupo nuevos
		try {
			userService.putUser(usuario);
			grupoServ.putGroup(group);
			System.out.println(usuario.getId());
			System.out.println(group.getId());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	@Test
	public void testAddGroupToUser() {

		Client client = Client.create();

		// usuario y grupo existente
		WebResource webResource = client
				.resource("http://localhost:8080/TB_Backend/api/user/"+usuario.getId()+"/group/"+group.getId());

		ClientResponse response = webResource.accept("application/json").put(
				ClientResponse.class);
		System.out.println(response);
		// comprobamos que la respuesta sea correcta
		assertTrue(response.getStatus() == 200);
		
		// comprobamos que el usuario y el grupo estan bien
		try {
			users = grupoServ.getGroup(group.getId()).getUsers();
			groups = userService.getUser(usuario.getId()).getGroups();
			System.out.println(grupoServ.getGroup(group.getId()).getUsers());
			assertTrue(users.contains(usuario.getId()));
			assertTrue(groups.contains(group.getId()));

			// eliminamos para proximas comprobaciones
			grupoServ.deleteUserToGroup(group.getId(), usuario.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// usuario NO existente y grupo existente
		WebResource webResource2 = client
				.resource("http://localhost:8080/TB_Backend/api/user/1234/group/"+group.getId());

		ClientResponse response2 = webResource2.accept("application/json").put(
				ClientResponse.class);

		// comprobamos que la respuesta sea item no encontrado
		assertTrue(response2.getStatus() == 404);

		// comprobamos que el usuario y el grupo estan bien
		try {
			users = grupoServ.getGroup(group.getId()).getUsers();
			groups = userService.getUser(usuario.getId()).getGroups();
			assertFalse(users.contains(usuario.getId()));
			assertFalse(groups.contains(group.getId()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		// usuario existente y grupo NO existente
		WebResource webResource3 = client
				.resource("http://localhost:8080/TB_Backend/api/user/"+usuario.getId()+"/group/44554816");

		ClientResponse response3 = webResource3.accept("application/json").put(
				ClientResponse.class);

		// comprobamos que la respuesta sea item no encontrado
		assertTrue(response3.getStatus() == 404);

		
		// comprobamos que el usuario y el grupo estan bien
		try {
			users = grupoServ.getGroup(group.getId()).getUsers();
			groups = userService.getUser(usuario.getId()).getGroups();
			assertFalse(users.contains(usuario.getId()));
			assertFalse(groups.contains(group.getId()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
