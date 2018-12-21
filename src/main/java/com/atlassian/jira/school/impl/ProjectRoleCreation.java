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
		if(projectRoleDoesNotExist("From-Teacher")) {
			projectRoleService.createProjectRole(
					new ProjectRoleImpl("From-Teacher", "Form-Teacher of a class' devision"), errorCollection);
		}
	}

	public void DivisionsProjectRole() {
		if(projectRoleDoesNotExist("A")) {
			projectRoleService.createProjectRole(new ProjectRoleImpl("A", "Division A"), errorCollection);
		}
		if(projectRoleDoesNotExist("B")) {
			projectRoleService.createProjectRole(new ProjectRoleImpl("B", "Division B"), errorCollection);
		}
		if(projectRoleDoesNotExist("V")) {
			projectRoleService.createProjectRole(new ProjectRoleImpl("V", "Division V"), errorCollection);
		}
		if(projectRoleDoesNotExist("G")) {
			projectRoleService.createProjectRole(new ProjectRoleImpl("G", "Division G"), errorCollection);
		}

	}

	private boolean projectRoleDoesNotExist(String arg) {
		return (projectRoleService.getProjectRoleByName(arg, errorCollection) == null);
	}

}
