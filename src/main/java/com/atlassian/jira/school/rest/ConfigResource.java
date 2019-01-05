package com.atlassian.jira.school.rest;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ofbiz.core.entity.GenericEntityException;

import com.atlassian.crowd.exception.OperationNotPermittedException;
import com.atlassian.crowd.exception.embedded.InvalidGroupException;
import com.atlassian.jira.school.impl.*;
import com.atlassian.jira.school.impl.schemes.IssueTypeSchemeCreation;
import com.atlassian.jira.school.impl.schemes.PermissionSchemeCreation;
import com.atlassian.jira.school.impl.schemes.WorkflowSchemeCreation;

@Path("/setup")
public class ConfigResource {

	private GroupCreation groupCreation;
	private ProjectRoleCreation projectRoleCreation;
	private IssueTypeCreation issueTypeCreation;
	private WorkflowCreation workflowCreation;

	private PermissionSchemeCreation permissionSchemeCreation;
	private IssueTypeSchemeCreation issueTypeSchemeCreation;
	private WorkflowSchemeCreation workflowSchemeCreation;

	public ConfigResource() {
		this.groupCreation = new GroupCreation();
		this.projectRoleCreation = new ProjectRoleCreation();
		this.issueTypeCreation = new IssueTypeCreation();
		this.workflowCreation = new WorkflowCreation();
		
		this.permissionSchemeCreation = new PermissionSchemeCreation();	
		this.issueTypeSchemeCreation = new IssueTypeSchemeCreation();
		this.workflowSchemeCreation = new WorkflowSchemeCreation();
		
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getMessage() {
		return Response.ok(new ConfigResourceModel("Hello Weary Traveller!")).build();
	}

	@POST
	public Response setupInstance() throws OperationNotPermittedException, InvalidGroupException, GenericEntityException {

//		groupCreation.studentsGroup();
//		groupCreation.teachersGroup();
//		groupCreation.classesGroup();
//		
//		projectRoleCreation.DivisionsProjectRole();
//		projectRoleCreation.FormTeacherProjectRole();
//		
//		permissionSchemeCreation.createPermissionScheme();
//
//		issueTypeCreation.subjectIssueType();
//		issueTypeCreation.lectureIssueType();
//		issueTypeCreation.homeworkIssueType();
//		issueTypeCreation.studentRecordIssueType();
//
//		issueTypeSchemeCreation.schoolIssueTypeScheme();
//		issueTypeSchemeCreation.studentIssueTypeScheme();

//		workflowCreation.createHomeworkWorkflow();
		
		workflowSchemeCreation.assigneHomeworkIssueTypeToHomeworkWorkflow();

//		issueTypeScreenSchemeCreation.createIssueTypeScreenScheme();
		
		return Response.created(null).build();
	}
}