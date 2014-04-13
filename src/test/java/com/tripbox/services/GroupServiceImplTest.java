package com.tripbox.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.UserNotExistOnGroup;
import com.tripbox.services.interfaces.UserService;

public class GroupServiceImplTest {
	
	static Querys bbdd = Mock.getInstance();
	
	static GroupServiceImpl grupoServ = new GroupServiceImpl();
	static UserService userService = new UserServiceImpl();
	static ArrayList<String> groups= new ArrayList<String>();
	static ArrayList<String> users= new ArrayList<String>();
	static Group testGroup;
	static Group resultGroup;
	static Group grupo1;
	static Group grupo2;
	static User usuario;
	static User userNotInGroup;
	
	@BeforeClass
	public static void SetUpBeforeClass() throws Exception{
		usuario = new User(null,"jo","ja","ji","gh", "lo", groups);
		userNotInGroup = new User(null,"fID", null, "userNotInGroupName", null, null, groups);
		
		users.add(usuario.getId());
		
		testGroup = new Group(null, "testGroup", "grupo para tests", users);

		grupo1 = new Group("","prueba1","nada", users);
		grupo2 = new Group(null,"prueba1","nada", users);

		grupoServ.putGroup(testGroup);
		grupoServ.putGroup(grupo2);
		
		userService.putUser(usuario);
	}
	
	@Before
	public void Setup(){
		resultGroup = null;
	}
	
	@Test
	public void testPutGroup() {
		
		try {
			grupoServ.putGroup(grupo1);
			assertNotNull(grupo1.getId());
			assertNotNull(grupoServ.getGroup(grupo1.getId()));
			
			users.add(usuario.getId());
			grupoServ.putGroup(grupo2);
			assertEquals(grupo2.getId(),"557842");
			assertNotNull(grupo2.getUsers());
			assertNotNull(grupoServ.getGroup(grupo2.getId()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetGroup() throws Exception {
		Group grupo;
		resultGroup = grupoServ.getGroup(testGroup.getId());
		
		assertEquals(resultGroup.getName(), "testGroup");
		
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
	public void testDeleteUserToGroup() throws Exception {
		
		//eliminamos usuario existente de grupo existente
		try {
			grupoServ.deleteUserToGroup(grupo2.getId(), usuario.getId());
			
		} catch (UserNotExistOnGroup e) {
			fail();
		} catch (InvalidIdsException e) {	
			fail();
		} catch (Exception e) {
			fail();
		}
		
		//eliminamos usuario existente de grupo INEXISTENTE (no tiene que funcionar)
		try {
			grupoServ.deleteUserToGroup("0000", usuario.getId());
			fail();
		} catch (Exception e) {
			
		}
		
		//eliminamos usuario INEXISTENTE de grupo existente (no tiene que funcionar)
		try {
			grupoServ.deleteUserToGroup(grupo2.getId(), userNotInGroup.getId());
			fail();
		} catch (InvalidIdsException e) {
			
		}
	}

	@After
	public void deleteUser() throws Exception{
		try {
			if ((resultGroup!=null)&&(resultGroup.getId()!=null)){
				grupoServ.deleteGroup(resultGroup.getId());
			}
		} catch (Exception e){
			fail();
		}
	}
	
	@AfterClass
	public static void tearDown(){

		try {
			userService.deleteUser(usuario.getId());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
