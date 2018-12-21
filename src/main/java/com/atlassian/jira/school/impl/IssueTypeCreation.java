package com.atlassian.jira.school.impl;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.IssueTypeManager;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;

import java.util.Collection;

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
				issueTypeManager.createIssueType("Lecture", "Lecture on subject", new Long(1));
			}
	}

	public void homeworkIssueType() {
			if(issueTypeDoesNotExist("Homework")) {
				issueTypeManager.createSubTaskIssueType("Homework", "Homework on lecture", new Long(1));
			}
	}

	public void studentRecordIssueType() {
			if(issueTypeDoesNotExist("Student Record")) {
				issueTypeManager.createIssueType("Student Record", "Single Student Record", new Long(1));
			}
	}

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
