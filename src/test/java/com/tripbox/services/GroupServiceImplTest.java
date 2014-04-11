package com.tripbox.services;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.services.GroupServiceImpl;
import com.tripbox.services.UserServiceImpl;
import com.tripbox.services.interfaces.UserService;

public class GroupServiceImplTest {
	
	static GroupServiceImpl grupoServ = new GroupServiceImpl();
	static UserService userService = new UserServiceImpl();
	static ArrayList<String> groups= new ArrayList<String>();
	static ArrayList<String> users= new ArrayList<String>();
	static Group grupo1;
	static Group grupo2;
	static User usuario;
	
	@BeforeClass
	public static void SetUp(){
		grupo1 = new Group("","prueba1","nada", users);
		grupo2 = new Group("557842","prueba1","nada", users);
		usuario = new User("8","jo","ja","ji","gh", "lo", groups);
		users.add(usuario.getId());
		grupo2.setUsers(users);
		groups.add(grupo1.getId());
		groups.add(grupo2.getId());
		usuario.setGroups(groups);
		
		//añadimos usuario para el deleteUserToGroup
		try {
			userService.putUser(usuario);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	@Test
	public void testPutGroup() {
		
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
	public void testGetGroup() throws Exception {
		Group grupo;
		grupo = grupoServ.getGroup("557842");
		assertEquals(grupo.getId(),"557842");
		
		try {
			grupoServ.getGroup("123");
			fail();   //no puede nunca encontrar este usuario
		} catch (ElementNotFoundException exc) {
			
		}
		
	}

	@Test
	public void testDeleteGroup() throws Exception {
		
		try{
			grupoServ.deleteGroup(grupo1.getId());
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
		
		//eliminamos usuario existente de grupo existente
		try {
			grupoServ.deleteUserToGroup(grupo2.getId(), usuario.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		
		//eliminamos usuario existente de grupo INEXISTENTE (no tiene que funcionar)
		try {
			grupoServ.deleteUserToGroup("0000", usuario.getId());
			fail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		//eliminamos usuario INEXISTENTE de grupo existente (no tiene que funcionar)
				try {
					grupoServ.deleteUserToGroup(grupo2.getId(), usuario.getId());
					fail();
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
	}

	
	@AfterClass
	public static void tearDown(){

		try {
			userService.deleteUser(usuario.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
