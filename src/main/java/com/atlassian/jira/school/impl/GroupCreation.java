package com.atlassian.jira.school.impl;

import com.atlassian.crowd.exception.OperationNotPermittedException;
import com.atlassian.crowd.exception.embedded.InvalidGroupException;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.groups.GroupManager;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;

// We create group not only for permission management but also for separating homeworks, lectures and so on
@Scanned
public class GroupCreation {

	private GroupManager groupManager;

	public GroupCreation() {
		groupManager = ComponentAccessor.getGroupManager();
	}

	public void teachersGroup() throws OperationNotPermittedException, InvalidGroupException {
		if(groupDoesNotExist("Teachers")) {
			groupManager.createGroup("Teachers");
		}
	}

	public void studentsGroup() throws OperationNotPermittedException, InvalidGroupException {
		if(groupDoesNotExist("Students")) {
			groupManager.createGroup("Students");
		}
	}

	// We create group for each year
	public void classesGroup() throws OperationNotPermittedException, InvalidGroupException {
		for (int i = 1; i <= 12; i++) {
			if (i == 1 && groupDoesNotExist(i + "st grade")) {
				groupManager.createGroup(i + "st grade");
			} else if (i == 2 && groupDoesNotExist(i + "nd grade")) {
				groupManager.createGroup(i + "nd grade");
			} else if (i!=1 && i!=2 && groupDoesNotExist(i + "th grade")) {
				groupManager.createGroup(i + "th grade");
			}
		}
	}

	// Check so we do not create a few groups with the same name as this is allowed in Jira
	private boolean groupDoesNotExist(String arg) {
		return (groupManager.getGroup(arg) == null);
	}
}
