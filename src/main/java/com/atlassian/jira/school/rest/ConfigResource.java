package com.atlassian.jira.school.rest;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.atlassian.crowd.exception.OperationNotPermittedException;
import com.atlassian.crowd.exception.embedded.InvalidGroupException;
import com.atlassian.jira.school.impl.GroupCreation;
import com.atlassian.jira.school.impl.IssueTypeCreation;
import com.atlassian.jira.school.impl.ProjectRoleCreation;

@Path("/setup")
public class ConfigResource {

	private GroupCreation groupCreation;
	private IssueTypeCreation issueTypeCreation;
	private ProjectRoleCreation projectRoleCreation;

	public ConfigResource() {
		this.groupCreation = new GroupCreation();
		this.issueTypeCreation = new IssueTypeCreation();
		this.projectRoleCreation = new ProjectRoleCreation();
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getMessage() {
		return Response.ok(new ConfigResourceModel("Hello World")).build();
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

		return Response.created(null).build();
	}
}