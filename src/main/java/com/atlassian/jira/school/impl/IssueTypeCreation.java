package com.atlassian.jira.school.impl;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.IssueTypeManager;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;

@Scanned
public class IssueTypeCreation {

	private IssueTypeManager issueTypeManager;

	public IssueTypeCreation() {
		this.issueTypeManager = ComponentAccessor.getComponent(IssueTypeManager.class);
	}

	public void subjectIssueType() {
		try {
			issueTypeManager.createIssueType("Subject", "Subject in school", new Long(1));
		} catch (IllegalStateException ignore) {
		}
	}

	public void lectureIssueType() {
		try {
			issueTypeManager.createIssueType("Lecture", "Lecture on subject", new Long(1));
		} catch (IllegalStateException ignore) {
		}
	}

	public void homeworkIssueType() {
		try {
			issueTypeManager.createSubTaskIssueType("Homework", "Homework on lecture", new Long(1));
		} catch (IllegalStateException ignore) {
		}
	}

	public void studentRecordIssueType() {
		try {
			issueTypeManager.createIssueType("Student Record", "Single Student Record", new Long(1));
		} catch (IllegalStateException ignore) {
		}
	}


}
