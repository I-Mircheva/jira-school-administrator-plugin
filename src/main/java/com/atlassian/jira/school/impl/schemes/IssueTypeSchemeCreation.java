package com.atlassian.jira.school.impl.schemes;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.fields.config.FieldConfigScheme;
import com.atlassian.jira.issue.fields.config.manager.IssueTypeSchemeManager;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;

import java.util.Collection;
import java.util.LinkedList;

@Scanned
public class IssueTypeSchemeCreation {

	private IssueTypeSchemeManager issueTypeSchemeManager;

	public IssueTypeSchemeCreation() {

		// IssueTypeSchemeManager allows us to use build in functions
		issueTypeSchemeManager = ComponentAccessor.getIssueTypeSchemeManager();
	}

	// We go trough all issue types and add them in the School scheme accordingly  
	public void schoolIssueTypeScheme() {

		LinkedList<String> listOfIssueTypeIDs = new LinkedList<>();

		Collection<IssueType> issueTypes = ComponentAccessor.getConstantsManager().getAllIssueTypeObjects();

		for (IssueType tmpIssueType : issueTypes) {
			if ((tmpIssueType.getName()).compareToIgnoreCase("Subject") == 0
					|| (tmpIssueType.getName()).compareToIgnoreCase("Lecture") == 0
					|| (tmpIssueType.getName()).compareToIgnoreCase("Homework") == 0) {
				listOfIssueTypeIDs.add(tmpIssueType.getId());
			}
		}

		for (FieldConfigScheme its : issueTypeSchemeManager.getAllSchemes()) {
			if(its.getName().equals("School")) {
				return;
			}
		}

		issueTypeSchemeManager.create("School", "School type issues", listOfIssueTypeIDs);
	}

	// Identical to the above function
	public void studentIssueTypeScheme() {

		LinkedList<String> listOfIssueTypeIDs = new LinkedList<>();

		Collection<IssueType> issueTypes = ComponentAccessor.getConstantsManager().getAllIssueTypeObjects();

		for (IssueType tmpIssueType : issueTypes) {
			if ((tmpIssueType.getName()).compareToIgnoreCase("Student Record") == 0) {
				listOfIssueTypeIDs.add(tmpIssueType.getId());
			}
		}

		for (FieldConfigScheme its : issueTypeSchemeManager.getAllSchemes()) {
			if(its.getName().equals("Student")) {
				return;
			}
		}


		issueTypeSchemeManager.create("Student", "Student record type issues", listOfIssueTypeIDs);

	}

}
