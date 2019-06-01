package com.atlassian.jira.school.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.atlassian.jira.bc.ServiceOutcome;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.StatusManager;
import com.atlassian.jira.issue.fields.screen.FieldScreen;
import com.atlassian.jira.issue.status.Status;
import com.atlassian.jira.issue.status.category.StatusCategory;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.workflow.ConfigurableJiraWorkflow;
import com.atlassian.jira.workflow.JiraWorkflow;
import com.atlassian.jira.workflow.WorkflowManager;
import com.atlassian.jira.workflow.edit.TransitionData;
import com.atlassian.jira.workflow.edit.Workflow;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;

@Scanned
public class WorkflowCreation {

	private WorkflowManager workflowManager;
	private StatusManager statusManager;
	private JiraAuthenticationContext authentication;
	private ApplicationUser user;

	public WorkflowCreation() {
		this.workflowManager = ComponentAccessor.getWorkflowManager();
		this.statusManager = ComponentAccessor.getComponent(StatusManager.class);
		this.authentication = ComponentAccessor.getJiraAuthenticationContext();
		this.user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
	}

	public void createHomeworkWorkflow() {
		if(workflowManager.getWorkflow("Homework Workflow") == null) {
			ConfigurableJiraWorkflow confWorkflow = new ConfigurableJiraWorkflow("Homework Workflow", workflowManager);
			confWorkflow.setDescription("Test workflow!?");

			workflowManager.createWorkflow(user, confWorkflow);

			createStatuses();
		}

		if(workflowManager.isActive(workflowManager.getWorkflow("Homework Workflow"))) {
			JiraWorkflow workflow = workflowManager.createDraftWorkflow(user, "Homework Workflow");

			Workflow newWorkflow = new Workflow(workflow, null, authentication, null, null, null);

			Collection<Status> linkedStatuses = workflow.getLinkedStatusObjects();

			Collection<Status> allStatuses = statusManager.getStatuses(); //getStatusesId();

			for (Iterator<Status> iterator = allStatuses.iterator(); iterator.hasNext(); ) {
				Status status = iterator.next();
				if(status.getName().equals("Closed") || status.getName().equals("Resolved")) continue;
				boolean temp = false;
				for(Iterator<Status> secIterator = linkedStatuses.iterator(); secIterator.hasNext(); ) {
					Status secStatus = secIterator.next();
					if(status.getName().equals(secStatus.getName())) temp = true;
				}
				if(!temp) newWorkflow.addStatus(statusManager.getStatus(status.getId()));
			}

			workflowManager.updateWorkflow(user, workflow);
			workflowManager.overwriteActiveWorkflow(user, "Homework Workflow");
			workflowManager.deleteDraftWorkflow("Homework Workflow");
		}
	}

	public void createStatuses() {
		statusManager.createStatus("Assigned", "A homework is assigned to a student.",
				StatusCategory.UNDEFINED);
		statusManager.createStatus("For revision", "The homework is handed in for revision to a teacher.",
				StatusCategory.UNDEFINED);
		statusManager.createStatus("Graded", "The homework is revised and graded by a teacher.",
				StatusCategory.UNDEFINED);
	}
}