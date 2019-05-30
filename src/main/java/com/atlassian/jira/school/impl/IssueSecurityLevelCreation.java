package com.atlassian.jira.school.impl;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.security.IssueSecurityLevelManager;
import com.atlassian.jira.issue.security.IssueSecuritySchemeManager;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;

@Scanned
public class IssueSecurityLevelCreation {

	private IssueSecurityLevelManager issueSecurityLevelManager;
	private IssueSecuritySchemeManager issueSecuritySchemeManager;
	
	public IssueSecurityLevelCreation() {
		this.issueSecurityLevelManager = ComponentAccessor.getIssueSecurityLevelManager();
		this.issueSecuritySchemeManager = ComponentAccessor.getComponent(IssueSecuritySchemeManager.class);
	}
	
	public void SchoolIssueSecurityLevel() {
		if(issueSecurityLevelManager.getIssueSecurityLevelsByName("School Security Level").size() < 1) {
			issueSecurityLevelManager.createIssueSecurityLevel(
					issueSecuritySchemeManager.getSchemeObject("School Security Scheme").getId(),
					"School Security Level", "Restricting access to issues");
		}
	}
	
	public void StudentRecordsIssueSecurityLevel() {
		if(issueSecurityLevelManager.getIssueSecurityLevelsByName("Student Records Security Level").size() < 1) {
			issueSecurityLevelManager.createIssueSecurityLevel(
					issueSecuritySchemeManager.getSchemeObject("Student Records Security Scheme").getId(),
					"Student Records Security Level", "Restricting access to issues");
		}
	}
}