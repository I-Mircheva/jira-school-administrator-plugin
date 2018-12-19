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
            issueTypeManager.createIssueType("Subject","Subject in school", new Long(1));
        } catch (IllegalStateException ignore) {}
    }
}
