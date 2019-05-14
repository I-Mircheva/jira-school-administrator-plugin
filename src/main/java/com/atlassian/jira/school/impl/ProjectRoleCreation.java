package com.atlassian.jira.school.impl;

import com.atlassian.jira.bc.projectroles.ProjectRoleService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.roles.ProjectRoleImpl;
import com.atlassian.jira.util.ErrorCollection;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;

// We use project roles for further separation of school classes and teachers so giving homeworks would be possible for example English and Spanish division
@Scanned
public class ProjectRoleCreation {

	private ProjectRoleService projectRoleService;
	private ErrorCollection errorCollection;

	public ProjectRoleCreation() {
		this.projectRoleService = ComponentAccessor.getComponent(ProjectRoleService.class);
	}

	public void FormTeacherProjectRole() {
		if(projectRoleDoesNotExist("From-Teacher")) {
			projectRoleService.createProjectRole(
					new ProjectRoleImpl("From-Teacher", "Form-Teacher of a class' division"), errorCollection);
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
