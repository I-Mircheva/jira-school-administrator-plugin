package com.atlassian.jira.school.rest;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.atlassian.crowd.exception.OperationNotPermittedException;
import com.atlassian.crowd.exception.embedded.InvalidGroupException;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.groups.GroupManager;

@Path("/setup")
public class ConfigResource {

	private GroupManager groupManager;
	
	public ConfigResource() {
		groupManager = ComponentAccessor.getGroupManager();
	}
	
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMessage()
    {
       return Response.ok(new ConfigResourceModel("Hello World")).build();
    }
    
    @POST
    public Response setupInstance() throws OperationNotPermittedException, InvalidGroupException {
    	groupManager.createGroup("Teachers");
    	return Response.created(null).build();
    }
}