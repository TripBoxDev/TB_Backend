package com.tripbox.api.interfaces;

import javax.ws.rs.core.Response;

import com.tripbox.elements.Group;

public interface GroupREST {
	
	public Response getGroup(String id);
	public Response putGroup(Group user);
	public void deleteGroup(String id);
}
