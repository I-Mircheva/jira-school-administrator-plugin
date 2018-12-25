package com.atlassian.jira.school.impl;


import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.permission.PermissionSchemeManager;
import com.atlassian.jira.scheme.Scheme;
import com.atlassian.jira.scheme.SchemeEntity;
import com.atlassian.jira.security.plugin.ProjectPermissionKey;
import com.atlassian.jira.security.roles.ProjectRole;
import com.atlassian.jira.security.roles.ProjectRoleManager;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import java.util.ArrayList;
import java.util.Collection;

import static com.atlassian.jira.bc.projectroles.ProjectRoleService.PROJECTROLE_PERMISSION_TYPE;
import static com.atlassian.jira.permission.ProjectPermissions.*;

@Scanned
public class PermissionCreation {

//	private static final String GROUP_PERMISSION_TYPE = "group";
	private PermissionSchemeManager permissionSchemeManager;

	public PermissionCreation() {
		permissionSchemeManager = ComponentAccessor.getPermissionSchemeManager();
	}


	private SchemeEntity entityBuilder(ProjectRole projectRole, ProjectPermissionKey permissionKey) {
		return new SchemeEntity(
				PROJECTROLE_PERMISSION_TYPE,
				projectRole.getId().toString(),
				permissionKey
		);
	}

//	private SchemeEntity entityBuilderG(Group group, ProjectPermissionKey permissionKey) {
//		return new SchemeEntity(
//				GROUP_PERMISSION_TYPE,
//				group.getName(),
//				permissionKey
//		);
//	}

	public void createPermissionScheme(GroupCreation groupCreation) {
		ProjectRoleManager projectRoleManager = ComponentAccessor.getComponent(ProjectRoleManager.class);
		ProjectRole administrators = projectRoleManager.getProjectRole("A");
		ProjectRole developers = projectRoleManager.getProjectRole("B");
		ProjectRole users = projectRoleManager.getProjectRole("From-Teacher");

//		Can't use group manager again because of java.lang.LinkageError: com/atlassian/crowd/embedded/api/Group

//		GroupManager groupManager = groupCreation.getGroupManager();
//		Group teachers = groupManager.getGroup("Teachers");

		Collection<SchemeEntity> schemeEntities = new ArrayList<>();

//		schemeEntities.add(entityBuilderG(teachers, ADD_COMMENTS));
//		schemeEntities.add(entityBuilderG(teachers, ADMINISTER_PROJECTS));


		schemeEntities.add(entityBuilder(administrators, ADD_COMMENTS));
		schemeEntities.add(entityBuilder(administrators, ADMINISTER_PROJECTS));
		schemeEntities.add(entityBuilder(administrators, ASSIGNABLE_USER));
		schemeEntities.add(entityBuilder(administrators, ASSIGN_ISSUES));
		schemeEntities.add(entityBuilder(administrators, BROWSE_PROJECTS));
		schemeEntities.add(entityBuilder(administrators, CLOSE_ISSUES));
		schemeEntities.add(entityBuilder(administrators, CREATE_ATTACHMENTS));
		schemeEntities.add(entityBuilder(administrators, CREATE_ISSUES));
		schemeEntities.add(entityBuilder(administrators, DELETE_ALL_ATTACHMENTS));
		schemeEntities.add(entityBuilder(administrators, DELETE_ALL_COMMENTS));
		schemeEntities.add(entityBuilder(administrators, DELETE_ALL_WORKLOGS));
		schemeEntities.add(entityBuilder(administrators, DELETE_ISSUES));
		schemeEntities.add(entityBuilder(administrators, EDIT_ALL_COMMENTS));
		schemeEntities.add(entityBuilder(administrators, EDIT_ALL_WORKLOGS));
		schemeEntities.add(entityBuilder(administrators, EDIT_ISSUES));
		schemeEntities.add(entityBuilder(administrators, LINK_ISSUES));
		schemeEntities.add(entityBuilder(administrators, MODIFY_REPORTER));
		schemeEntities.add(entityBuilder(administrators, MOVE_ISSUES));
		schemeEntities.add(entityBuilder(administrators, RESOLVE_ISSUES));
		schemeEntities.add(entityBuilder(administrators, SCHEDULE_ISSUES));
		schemeEntities.add(entityBuilder(administrators, SET_ISSUE_SECURITY));
		schemeEntities.add(entityBuilder(administrators, TRANSITION_ISSUES));
		schemeEntities.add(entityBuilder(administrators, VIEW_DEV_TOOLS));
		schemeEntities.add(entityBuilder(administrators, VIEW_READONLY_WORKFLOW));
		schemeEntities.add(entityBuilder(administrators, VIEW_VOTERS_AND_WATCHERS));
		schemeEntities.add(entityBuilder(administrators, WORK_ON_ISSUES));

		schemeEntities.add(entityBuilder(developers, ADD_COMMENTS));
		schemeEntities.add(entityBuilder(developers, ASSIGNABLE_USER));
		schemeEntities.add(entityBuilder(developers, ASSIGN_ISSUES));
		schemeEntities.add(entityBuilder(developers, BROWSE_PROJECTS));
		schemeEntities.add(entityBuilder(developers, CLOSE_ISSUES));
		schemeEntities.add(entityBuilder(developers, CREATE_ATTACHMENTS));
		schemeEntities.add(entityBuilder(developers, CREATE_ISSUES));
		schemeEntities.add(entityBuilder(developers, DELETE_OWN_ATTACHMENTS));
		schemeEntities.add(entityBuilder(developers, DELETE_OWN_COMMENTS));
		schemeEntities.add(entityBuilder(developers, DELETE_OWN_WORKLOGS));
		schemeEntities.add(entityBuilder(developers, EDIT_ISSUES));
		schemeEntities.add(entityBuilder(developers, EDIT_OWN_COMMENTS));
		schemeEntities.add(entityBuilder(developers, EDIT_OWN_WORKLOGS));
		schemeEntities.add(entityBuilder(developers, LINK_ISSUES));
		schemeEntities.add(entityBuilder(developers, MANAGE_WATCHERS));
		schemeEntities.add(entityBuilder(developers, MODIFY_REPORTER));
		schemeEntities.add(entityBuilder(developers, MOVE_ISSUES));
		schemeEntities.add(entityBuilder(developers, RESOLVE_ISSUES));
		schemeEntities.add(entityBuilder(developers, SCHEDULE_ISSUES));
		schemeEntities.add(entityBuilder(developers, SET_ISSUE_SECURITY));
		schemeEntities.add(entityBuilder(developers, TRANSITION_ISSUES));
		schemeEntities.add(entityBuilder(developers, VIEW_DEV_TOOLS));
		schemeEntities.add(entityBuilder(developers, VIEW_READONLY_WORKFLOW));
		schemeEntities.add(entityBuilder(developers, VIEW_VOTERS_AND_WATCHERS));
		schemeEntities.add(entityBuilder(developers, WORK_ON_ISSUES));

		schemeEntities.add(entityBuilder(users, ADD_COMMENTS));
		schemeEntities.add(entityBuilder(users, BROWSE_PROJECTS));
		schemeEntities.add(entityBuilder(users, CREATE_ATTACHMENTS));
		schemeEntities.add(entityBuilder(users, CREATE_ISSUES));
		schemeEntities.add(entityBuilder(users, EDIT_OWN_COMMENTS));
		schemeEntities.add(entityBuilder(users, LINK_ISSUES));
		schemeEntities.add(entityBuilder(users, MANAGE_WATCHERS));
		schemeEntities.add(entityBuilder(users, VIEW_VOTERS_AND_WATCHERS));

		schemeEntities.add(new SchemeEntity("lead", ADMINISTER_PROJECTS));
		schemeEntities.add(new SchemeEntity("projectrole", administrators.getId().toString(), "MANAGE_SPRINTS_PERMISSION"));
		schemeEntities.add(new SchemeEntity("projectrole", developers.getId().toString(), "MANAGE_SPRINTS_PERMISSION"));

		Long nextId = permissionSchemeManager.getSchemeObjects()
				.stream()
				.reduce((a, b) -> b)
				.get()
				.getId() + 1;

		Scheme draftScheme = new Scheme(nextId, "PermissionScheme", "ID Permission Scheme", schemeEntities);

		permissionSchemeManager.createSchemeAndEntities(draftScheme);
	}
}
