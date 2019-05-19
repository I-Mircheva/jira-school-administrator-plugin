package com.atlassian.jira.school.impl.schemes;

import java.util.Collection;
import java.util.Iterator;

import org.ofbiz.core.entity.GenericEntityException;
import org.ofbiz.core.entity.GenericValue;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.IssueTypeManager;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.workflow.WorkflowSchemeManager;

public class WorkflowSchemeCreation {

	private WorkflowSchemeManager workflowSchemeManager;
	private IssueTypeManager issueTypeManager;
	private ProjectManager projectManager;

	public WorkflowSchemeCreation() {
		this.workflowSchemeManager = ComponentAccessor.getWorkflowSchemeManager();
		this.issueTypeManager = ComponentAccessor.getComponent(IssueTypeManager.class);
		this.projectManager = ComponentAccessor.getProjectManager();
	}
	
	public void assigneHomeworkIssueTypeToHomeworkWorkflow() throws GenericEntityException {
		GenericValue scheme = workflowSchemeManager.createScheme("Homework Workflow Scheme", "TEST");
		
		Collection<IssueType> list = issueTypeManager.getIssueTypes();
		
		for(Iterator<IssueType> iterator = list.iterator(); iterator.hasNext(); ) {
			IssueType issueType = (IssueType) iterator.next();
			if (issueType.getName().equals("Homework")) {
				workflowSchemeManager.addWorkflowToScheme(scheme, "Homework Workflow", issueType.getId());
			}	
		}
	}
}
