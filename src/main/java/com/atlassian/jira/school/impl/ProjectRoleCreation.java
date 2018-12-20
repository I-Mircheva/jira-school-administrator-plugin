package com.atlassian.jira.school.impl;

import com.atlassian.jira.bc.projectroles.ProjectRoleService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.roles.ProjectRoleImpl;
import com.atlassian.jira.util.ErrorCollection;

public class ProjectRoleCreation {

	private ProjectRoleService projectRoleService;
	private ErrorCollection errorCollection;

	public ProjectRoleCreation() {
		this.projectRoleService = ComponentAccessor.getComponent(ProjectRoleService.class);
	}

	public void FormTeacherProjectRole() {
		projectRoleService.createProjectRole(
				new ProjectRoleImpl("From-Teacher", "Form-Teacher of a class' devision"), errorCollection);
	}

	public void DivisionsProjectRole() {
		projectRoleService.createProjectRole(new ProjectRoleImpl("A", "Division A"), errorCollection);
		projectRoleService.createProjectRole(new ProjectRoleImpl("B", "Division B"), errorCollection);
		projectRoleService.createProjectRole(new ProjectRoleImpl("V", "Division V"), errorCollection);
		projectRoleService.createProjectRole(new ProjectRoleImpl("G", "Division G"), errorCollection);
	}

}
