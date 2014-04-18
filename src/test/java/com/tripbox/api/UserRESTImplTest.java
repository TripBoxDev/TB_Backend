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
	static ArrayList<String> list = new ArrayList<String>();
	static ArrayList<String> list2 = new ArrayList<String>();

	@Test
	public void testAddGroupToUser() {

		// eliminamos el usuario del grupo para despues volver a ponerlo y
		// comprobamos que se haya borrado
		try {
			grupoServ.deleteUserToGroup("445566", "123456");
			list = grupoServ.getGroup("445566").getUsers();
			list2 = userService.getUser("123456").getGroups();
			assertFalse(list.contains("123456"));
			assertFalse(list2.contains("445566"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Client client = Client.create();

		// usuario y grupo existente
		WebResource webResource = client
				.resource("http://localhost:8080/TB_Backend/api/user/123456/group/445566");

		ClientResponse response = webResource.accept("application/json").put(
				ClientResponse.class);

		// comprobamos que la respuesta sea correcta
		assertTrue(response.getStatus() == 200);
		
		// comprobamos que el usuario y el grupo estan bien
		try {
			list = grupoServ.getGroup("445566").getUsers();
			list2 = userService.getUser("123456").getGroups();
			System.out.println(grupoServ.getGroup("445566").getUsers());
			assertTrue(list.contains("123456"));
			assertTrue(list2.contains("445566"));

			// eliminamos para proximas comprobaciones
			grupoServ.deleteUserToGroup("445566", "123456");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// usuario NO existente y grupo existente
		WebResource webResource2 = client
				.resource("http://localhost:8080/TB_Backend/api/user/1234/group/445566");

		ClientResponse response2 = webResource2.accept("application/json").put(
				ClientResponse.class);

		// comprobamos que la respuesta sea item no encontrado
		assertTrue(response2.getStatus() == 404);

		// comprobamos que el usuario y el grupo estan bien
		try {
			list = grupoServ.getGroup("445566").getUsers();
			list2 = userService.getUser("123456").getGroups();
			assertFalse(list.contains("123456"));
			assertFalse(list2.contains("445566"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		// usuario existente y grupo NO existente
		WebResource webResource3 = client
				.resource("http://localhost:8080/TB_Backend/api/user/123456/group/44554816");

		ClientResponse response3 = webResource3.accept("application/json").put(
				ClientResponse.class);

		// comprobamos que la respuesta sea item no encontrado
		assertTrue(response3.getStatus() == 404);

		
		// comprobamos que el usuario y el grupo estan bien
		try {
			list = grupoServ.getGroup("445566").getUsers();
			list2 = userService.getUser("123456").getGroups();
			assertFalse(list.contains("123456"));
			assertFalse(list2.contains("445566"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
