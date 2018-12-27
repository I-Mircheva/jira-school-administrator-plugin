package com.atlassian.jira.school.rest;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.atlassian.crowd.exception.OperationNotPermittedException;
import com.atlassian.crowd.exception.embedded.InvalidGroupException;
import com.atlassian.jira.permission.PermissionScheme;
import com.atlassian.jira.school.impl.*;

@Path("/setup")
public class ConfigResource {

	private GroupCreation groupCreation;
	private IssueTypeCreation issueTypeCreation;
	private ProjectRoleCreation projectRoleCreation;
	private PermissionCreation permissionCreation;
	private IssueTypeSchemeCreation issueTypeSchemeCreation;

	public ConfigResource() {
		this.groupCreation = new GroupCreation();
		this.issueTypeCreation = new IssueTypeCreation();
		this.projectRoleCreation = new ProjectRoleCreation();
		this.permissionCreation = new PermissionCreation();
		this.issueTypeSchemeCreation = new IssueTypeSchemeCreation();

	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getMessage() {
		return Response.ok(new ConfigResourceModel("Hello Weary Traveller!")).build();
	}

	@POST
	public Response setupInstance() throws OperationNotPermittedException, InvalidGroupException {

		projectRoleCreation.DivisionsProjectRole();
		projectRoleCreation.FormTeacherProjectRole();

		issueTypeCreation.subjectIssueType();
		issueTypeCreation.lectureIssueType();
		issueTypeCreation.homeworkIssueType();
		issueTypeCreation.studentRecordIssueType();

		groupCreation.studentsGroup();
		groupCreation.teachersGroup();
		groupCreation.classesGroup();

		issueTypeSchemeCreation.schoolIssueTypeScheme();
		issueTypeSchemeCreation.studentIssueTypeScheme();

		permissionCreation.createPermissionScheme(groupCreation);

		return Response.created(null).build();
	}
}