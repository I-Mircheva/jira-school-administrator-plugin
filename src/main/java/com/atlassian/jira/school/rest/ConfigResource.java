package com.atlassian.jira.school.rest;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ofbiz.core.entity.GenericEntityException;

import com.atlassian.crowd.exception.OperationNotPermittedException;
import com.atlassian.crowd.exception.embedded.InvalidGroupException;
import com.atlassian.jira.school.impl.GroupCreation;
import com.atlassian.jira.school.impl.IssueSecurityLevelCreation;
import com.atlassian.jira.school.impl.IssueTypeCreation;
import com.atlassian.jira.school.impl.ProjectRoleCreation;
import com.atlassian.jira.school.impl.WorkflowCreation;
import com.atlassian.jira.school.impl.schemes.IssueSecuritySchemeCreation;
import com.atlassian.jira.school.impl.schemes.IssueTypeSchemeCreation;
import com.atlassian.jira.school.impl.schemes.IssueTypeScreenSchemeCreation;
import com.atlassian.jira.school.impl.schemes.PermissionSchemeCreation;
import com.atlassian.jira.school.impl.schemes.WorkflowSchemeCreation;

@Path("/setup")
public class ConfigResource {

	private GroupCreation groupCreation;
	private ProjectRoleCreation projectRoleCreation;
	private IssueTypeCreation issueTypeCreation;
	private IssueSecurityLevelCreation issueSecurityLevelCreation;
	private WorkflowCreation workflowCreation;

	private PermissionSchemeCreation permissionSchemeCreation;
	private IssueTypeSchemeCreation issueTypeSchemeCreation;
	private IssueTypeScreenSchemeCreation issueTypeScreenSchemeCreation;
	private IssueSecuritySchemeCreation issueSecuritySchemeCreation;
	private WorkflowSchemeCreation workflowSchemeCreation;

	public ConfigResource() {
		this.groupCreation = new GroupCreation();
		this.projectRoleCreation = new ProjectRoleCreation();
		this.issueTypeCreation = new IssueTypeCreation();
		this.issueSecurityLevelCreation = new IssueSecurityLevelCreation();
		this.workflowCreation = new WorkflowCreation();
		
		this.permissionSchemeCreation = new PermissionSchemeCreation();	
		this.issueTypeSchemeCreation = new IssueTypeSchemeCreation();
		this.issueTypeScreenSchemeCreation = new IssueTypeScreenSchemeCreation();
		this.issueSecuritySchemeCreation = new IssueSecuritySchemeCreation();
		this.workflowSchemeCreation = new WorkflowSchemeCreation();
		
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getMessage() {
		return Response.ok(new ConfigResourceModel("Hello Weary Traveller!")).build();
	}

	@POST
	public Response setupInstance() throws OperationNotPermittedException, InvalidGroupException, GenericEntityException {

		groupCreation.studentsGroup();
		groupCreation.teachersGroup();
		groupCreation.classesGroup();

		projectRoleCreation.DivisionsProjectRole();
		projectRoleCreation.FormTeacherProjectRole();

		permissionSchemeCreation.createSchoolPermissionScheme();
		permissionSchemeCreation.createStudentRecordsPermissionScheme();
		
		issueTypeCreation.subjectIssueType();
		issueTypeCreation.lectureIssueType();
		issueTypeCreation.homeworkIssueType();
		issueTypeCreation.studentRecordIssueType();

		issueTypeSchemeCreation.schoolIssueTypeScheme();
		issueTypeSchemeCreation.studentIssueTypeScheme();

		issueSecuritySchemeCreation.SchoolIssueSecurityScheme();
		issueSecuritySchemeCreation.StudentRecordsIssueSecurityScheme();
		
		issueSecurityLevelCreation.SchoolIssueSecurityLevel();
		issueSecurityLevelCreation.StudentRecordsIssueSecurityLevel();
		
		workflowCreation.createHomeworkWorkflow();
		
		workflowSchemeCreation.assigneHomeworkIssueTypeToHomeworkWorkflow();

		issueTypeScreenSchemeCreation.createIssueTypeScreenScheme();
		
		return Response.created(null).build();
	}
}