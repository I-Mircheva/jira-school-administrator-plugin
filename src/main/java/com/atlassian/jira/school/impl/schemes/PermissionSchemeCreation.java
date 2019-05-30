package com.atlassian.jira.school.impl.schemes;

import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.permission.PermissionSchemeManager;
import com.atlassian.jira.scheme.Scheme;
import com.atlassian.jira.scheme.SchemeEntity;
import com.atlassian.jira.security.groups.GroupManager;
import com.atlassian.jira.security.plugin.ProjectPermissionKey;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import java.util.ArrayList;
import java.util.Collection;

import static com.atlassian.jira.permission.ProjectPermissions.*;

// The permission scheme is used to give certain users and groups their permissions 
@Scanned
public class PermissionSchemeCreation {

	private PermissionSchemeManager permissionSchemeManager;
	private ApplicationUser applicationUser;
	private GroupManager groupManager;

	public PermissionSchemeCreation() {
		permissionSchemeManager = ComponentAccessor.getPermissionSchemeManager();
		// Get the current user
		applicationUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
		// Access the groups via the manager
		groupManager = ComponentAccessor.getGroupManager();
	}

	// The scheme consists of entities which need to be created individually 
	private SchemeEntity entityBuilder(Group group, ProjectPermissionKey permissionKey) {
		return new SchemeEntity(
				"group",
				group.getName(),
				permissionKey
		);
	}

	public void createSchoolPermissionScheme() {

		Group teachers = groupManager.getGroup("Teachers");
		Group students = groupManager.getGroup("Students");

		Collection<SchemeEntity> schemeEntities = new ArrayList<>();

		if(applicationUser.getName().equals("admin")) {
			schemeEntities.add(new SchemeEntity("user", applicationUser.getName(), ADMINISTER_PROJECTS));
			schemeEntities.add(new SchemeEntity("user", applicationUser.getName(), BROWSE_PROJECTS));
		}
		
		schemeEntities.add(entityBuilder(teachers, ADD_COMMENTS));
		schemeEntities.add(entityBuilder(teachers, ADMINISTER_PROJECTS));
		schemeEntities.add(entityBuilder(teachers, ASSIGNABLE_USER));
		schemeEntities.add(entityBuilder(teachers, ASSIGN_ISSUES));
		schemeEntities.add(entityBuilder(teachers, BROWSE_PROJECTS));
		schemeEntities.add(entityBuilder(teachers, CLOSE_ISSUES));
		schemeEntities.add(entityBuilder(teachers, CREATE_ATTACHMENTS));
		schemeEntities.add(entityBuilder(teachers, CREATE_ISSUES));
		schemeEntities.add(entityBuilder(teachers, DELETE_ISSUES));
		schemeEntities.add(entityBuilder(teachers, DELETE_OWN_ATTACHMENTS));
		schemeEntities.add(entityBuilder(teachers, DELETE_OWN_COMMENTS));
		schemeEntities.add(entityBuilder(teachers, EDIT_ISSUES));
		schemeEntities.add(entityBuilder(teachers, EDIT_OWN_COMMENTS));
		schemeEntities.add(entityBuilder(teachers, LINK_ISSUES));
		schemeEntities.add(entityBuilder(teachers, MANAGE_WATCHERS));
		schemeEntities.add(entityBuilder(teachers, MODIFY_REPORTER));
		schemeEntities.add(entityBuilder(teachers, MOVE_ISSUES));
		schemeEntities.add(entityBuilder(teachers, RESOLVE_ISSUES));
		schemeEntities.add(entityBuilder(teachers, SCHEDULE_ISSUES));
		schemeEntities.add(entityBuilder(teachers, SET_ISSUE_SECURITY));
		schemeEntities.add(entityBuilder(teachers, TRANSITION_ISSUES));
		schemeEntities.add(entityBuilder(teachers, VIEW_READONLY_WORKFLOW));
		schemeEntities.add(entityBuilder(teachers, VIEW_VOTERS_AND_WATCHERS));
		schemeEntities.add(entityBuilder(teachers, WORK_ON_ISSUES));

		schemeEntities.add(entityBuilder(students, ADD_COMMENTS));
		schemeEntities.add(entityBuilder(students, ASSIGNABLE_USER));
		schemeEntities.add(entityBuilder(students, BROWSE_PROJECTS));
		schemeEntities.add(entityBuilder(students, CREATE_ATTACHMENTS));
		schemeEntities.add(entityBuilder(students, DELETE_OWN_ATTACHMENTS));
		schemeEntities.add(entityBuilder(students, DELETE_OWN_COMMENTS));
		schemeEntities.add(entityBuilder(students, EDIT_OWN_COMMENTS));
		schemeEntities.add(entityBuilder(students, MANAGE_WATCHERS));
		schemeEntities.add(entityBuilder(students, VIEW_READONLY_WORKFLOW));


		Long nextId = permissionSchemeManager.getSchemeObjects()
				.stream()
				.reduce((a, b) -> b)
				.get()
				.getId() + 1;

		// We put all entities in one scheme so we can easily add it to a project
		Scheme draftScheme = new Scheme(nextId, "PermissionScheme", "School Permission Scheme", schemeEntities);

		if(permissionSchemeManager.getSchemeObject("School Permission Scheme") == null) {
			permissionSchemeManager.createSchemeAndEntities(draftScheme);
		}
	}
	
	public void createStudentRecordsPermissionScheme() {

		Group teachers = groupManager.getGroup("Teachers");

		Collection<SchemeEntity> schemeEntities = new ArrayList<>();

		if(applicationUser.getName().equals("admin")) {
			schemeEntities.add(new SchemeEntity("user", applicationUser.getName(), ADMINISTER_PROJECTS));
			schemeEntities.add(new SchemeEntity("user", applicationUser.getName(), BROWSE_PROJECTS));
		}
		
		schemeEntities.add(entityBuilder(teachers, ADD_COMMENTS));
		schemeEntities.add(entityBuilder(teachers, ADMINISTER_PROJECTS));
		schemeEntities.add(entityBuilder(teachers, ASSIGNABLE_USER));
		schemeEntities.add(entityBuilder(teachers, ASSIGN_ISSUES));
		schemeEntities.add(entityBuilder(teachers, BROWSE_PROJECTS));
		schemeEntities.add(entityBuilder(teachers, CLOSE_ISSUES));
		schemeEntities.add(entityBuilder(teachers, CREATE_ATTACHMENTS));
		schemeEntities.add(entityBuilder(teachers, CREATE_ISSUES));
		schemeEntities.add(entityBuilder(teachers, DELETE_ISSUES));
		schemeEntities.add(entityBuilder(teachers, DELETE_OWN_ATTACHMENTS));
		schemeEntities.add(entityBuilder(teachers, DELETE_OWN_COMMENTS));
		schemeEntities.add(entityBuilder(teachers, EDIT_ISSUES));
		schemeEntities.add(entityBuilder(teachers, EDIT_OWN_COMMENTS));
		schemeEntities.add(entityBuilder(teachers, LINK_ISSUES));
		schemeEntities.add(entityBuilder(teachers, MANAGE_WATCHERS));
		schemeEntities.add(entityBuilder(teachers, MODIFY_REPORTER));
		schemeEntities.add(entityBuilder(teachers, MOVE_ISSUES));
		schemeEntities.add(entityBuilder(teachers, RESOLVE_ISSUES));
		schemeEntities.add(entityBuilder(teachers, SCHEDULE_ISSUES));
		schemeEntities.add(entityBuilder(teachers, SET_ISSUE_SECURITY));
		schemeEntities.add(entityBuilder(teachers, TRANSITION_ISSUES));
		schemeEntities.add(entityBuilder(teachers, VIEW_READONLY_WORKFLOW));
		schemeEntities.add(entityBuilder(teachers, VIEW_VOTERS_AND_WATCHERS));
		schemeEntities.add(entityBuilder(teachers, WORK_ON_ISSUES));


		Long nextId = permissionSchemeManager.getSchemeObjects()
				.stream()
				.reduce((a, b) -> b)
				.get()
				.getId() + 1;

		Scheme draftScheme = new Scheme(nextId, "PermissionScheme", "Student Records Permission Scheme", schemeEntities);

		if(permissionSchemeManager.getSchemeObject("Student Records Permission Scheme") == null) {
			permissionSchemeManager.createSchemeAndEntities(draftScheme);
		}
	}
}
