package com.atlassian.jira.school.impl;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.IssueTypeManager;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;

import java.util.Collection;


// We use the different issue types so it is easy to filter them out and to create them using screens with fields specific for them
@Scanned
public class IssueTypeCreation {

	private IssueTypeManager issueTypeManager;

	public IssueTypeCreation() {
		this.issueTypeManager = ComponentAccessor.getComponent(IssueTypeManager.class);
	}

	public void subjectIssueType() {
			if(issueTypeDoesNotExist("Subject")) {
				issueTypeManager.createIssueType("Subject", "Subject in school", new Long(1));
			}
	}

	public void lectureIssueType() {
			if(issueTypeDoesNotExist("Lecture")) {
				issueTypeManager.createIssueType("Lecture", "Lecture on subject", new Long(2));
			}
	}

	public void homeworkIssueType() {
			if(issueTypeDoesNotExist("Homework")) {
				issueTypeManager.createSubTaskIssueType("Homework", "Homework on lecture", new Long(3));
			}
	}

	public void studentRecordIssueType() {
			if(issueTypeDoesNotExist("Student Record")) {
				issueTypeManager.createIssueType("Student Record", "Single Student Record", new Long(4));
			}
	}

	// As we do not have a simple get issue type returning null as in groups we use a loop to check
	private boolean issueTypeDoesNotExist(String arg) {

		Collection<IssueType> issueTypes = ComponentAccessor.getConstantsManager().getAllIssueTypeObjects();

		for (IssueType tmpIssueType : issueTypes) {
			if ((tmpIssueType.getName()).compareToIgnoreCase(arg) == 0) {
				return false;
			}
		}
		return true;
	}


}
