package com.tripbox.services;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.services.GroupServiceImpl;
import com.tripbox.services.UserServiceImpl;
import com.tripbox.services.interfaces.UserService;

public class GroupServiceImplTest {
	
	@Test
	public void testGetGroup() throws Exception {
		GroupServiceImpl grupoServ = new GroupServiceImpl();
		Group grupo = new Group();
		grupo = grupoServ.getGroup("445566");
		assertEquals(grupo.getId(),"445566");
		
		try {
			grupoServ.getGroup("123");
			fail();   //no puede nunca encontrar este usuario
		} catch (ElementNotFoundException exc) {
			
		}
		
	}

	@Test
	public void testPutGroup() {
		GroupServiceImpl grupoServ = new GroupServiceImpl();
		ArrayList<String> users= new ArrayList<String>();
		Group grupo1 = new Group("","prueba1","nada", users);
		Group grupo2 = new Group("557842","prueba1","nada", users);
		
		try {
			grupoServ.putGroup(grupo1);
			assertNotNull(grupo1.getId());
			assertNotNull(grupoServ.getGroup(grupo1.getId()));
			
			grupoServ.putGroup(grupo2);
			assertEquals(grupo2.getId(),"557842");
			assertNotNull(grupoServ.getGroup(grupo2.getId()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testDeleteGroup() throws Exception {
		GroupServiceImpl grupoServ = new GroupServiceImpl();
		ArrayList<String> users= new ArrayList<String>();
		Group grupo = new Group("12345","prueba1","nada", users);
		grupoServ.putGroup(grupo);
		try{
			grupoServ.deleteGroup(grupo.getId());
		} catch(ElementNotFoundException e){
			fail();		//El grupo existe, asiq ue no tiene que fallar
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			grupoServ.deleteGroup("52");
			fail();		//No existe el grupo, asi que tiene que fallar
		} catch(ElementNotFoundException e){
		} catch (Exception e) {
		}
	}

	@Test
	public void testDeleteUserToGroup() {
		UserService userService = new UserServiceImpl();
		GroupServiceImpl grupoServ = new GroupServiceImpl();
		ArrayList<String> users= new ArrayList<String>();
		users.add("8");
		Group grupo = new Group("1254862","prueba1","nada", users);
		ArrayList<String> groups= new ArrayList<String>();
		groups.add("1254862");
		User usuario = new User ("8", null, null, "jo","ja","jar",groups);
		
		//a?adimos usuario
		try {
			userService.putUser(usuario);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//a?adimos grupo
		try {
			grupoServ.putGroup(grupo);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//eliminamos usuario existente de grupo existente
		try {
			grupoServ.deleteUserToGroup("1254862", "8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		
		//eliminamos usuario existente de grupo INEXISTENTE (no tiene que funcionar)
		try {
			grupoServ.deleteUserToGroup("0000", "8");
			fail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		//eliminamos usuario INEXISTENTE de grupo existente (no tiene que funcionar)
				try {
					grupoServ.deleteUserToGroup("1254862", "05125");
					fail();
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
	}

}
