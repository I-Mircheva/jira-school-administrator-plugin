package com.atlassian.jira.school.impl;

import com.atlassian.crowd.exception.OperationNotPermittedException;
import com.atlassian.crowd.exception.embedded.InvalidGroupException;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.groups.GroupManager;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;

@Scanned
public class GroupCreation {

	private GroupManager groupManager;

	public GroupCreation() {
		groupManager = ComponentAccessor.getGroupManager();
	}

	public void teachersGroup() throws OperationNotPermittedException, InvalidGroupException {
			groupManager.createGroup("Teachers");
	}

	public void studentsGroup() throws OperationNotPermittedException, InvalidGroupException {
			groupManager.createGroup("Students");

	}

	public void classesGroup() throws OperationNotPermittedException, InvalidGroupException {
		for(int i = 1; i <= 12; i++) {
			if (i == 1) { 
				groupManager.createGroup(i + "st grade");
			} else if (i == 2) {
				groupManager.createGroup(i + "nd grade");
			} else {
				groupManager.createGroup(i + "th grade");
			}
		}
	}
	
	private boolean ifGroupExists(String arg) {
		return (groupManager.getGroup(arg) != null);
	}
}
