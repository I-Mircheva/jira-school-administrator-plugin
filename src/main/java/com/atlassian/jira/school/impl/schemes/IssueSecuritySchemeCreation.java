package com.atlassian.jira.school.impl.schemes;

import org.ofbiz.core.entity.GenericEntityException;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.security.IssueSecuritySchemeManagerImpl;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;

@Scanned
public class IssueSecuritySchemeCreation {

	private IssueSecuritySchemeManagerImpl issueSecuritySchemeManagerImpl;
	
	public IssueSecuritySchemeCreation() {
		this.issueSecuritySchemeManagerImpl = ComponentAccessor
				.getComponent(IssueSecuritySchemeManagerImpl.class);
	}

	public void SchoolIssueSecurityScheme() throws GenericEntityException {
		if(!issueSecuritySchemeManagerImpl.schemeExists("School Security Scheme")) {
			issueSecuritySchemeManagerImpl.createScheme("School Security Scheme", "Restricting access");
		}
	}
	
	public void StudentRecordsIssueSecurityScheme() throws GenericEntityException {
		if(!issueSecuritySchemeManagerImpl.schemeExists("Student Records Security Scheme")) {
			issueSecuritySchemeManagerImpl.createScheme("Student Records Security Scheme", "Restricting access");
		}
	}
	
}
