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
	private ProjectRoleCreation projectRoleCreation;
	private PermissionCreation permissionCreation;
	private IssueTypeCreation issueTypeCreation;
	private IssueTypeSchemeCreation issueTypeSchemeCreation;
	private WorkflowCreation workflowCreation;

	public ConfigResource() {
		this.groupCreation = new GroupCreation();
		this.projectRoleCreation = new ProjectRoleCreation();
		this.permissionCreation = new PermissionCreation();
		this.issueTypeCreation = new IssueTypeCreation();
		this.issueTypeSchemeCreation = new IssueTypeSchemeCreation();
		this.workflowCreation = new WorkflowCreation();

	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getMessage() {
		return Response.ok(new ConfigResourceModel("Hello Weary Traveller!")).build();
	}

	@POST
	public Response setupInstance() throws OperationNotPermittedException, InvalidGroupException {

		groupCreation.studentsGroup();
		groupCreation.teachersGroup();
		groupCreation.classesGroup();
		
		projectRoleCreation.DivisionsProjectRole();
		projectRoleCreation.FormTeacherProjectRole();
		
		permissionCreation.createPermissionScheme();

		issueTypeCreation.subjectIssueType();
		issueTypeCreation.lectureIssueType();
		issueTypeCreation.homeworkIssueType();
		issueTypeCreation.studentRecordIssueType();

		issueTypeSchemeCreation.schoolIssueTypeScheme();
		issueTypeSchemeCreation.studentIssueTypeScheme();

//		workflowCreation.createHomeworkWorkflow();

		return Response.created(null).build();
	}
}