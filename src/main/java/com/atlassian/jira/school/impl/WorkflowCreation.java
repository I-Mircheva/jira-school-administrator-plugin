package com.atlassian.jira.school.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.StatusManager;
import com.atlassian.jira.issue.status.Status;
import com.atlassian.jira.issue.status.category.StatusCategory;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.workflow.ConfigurableJiraWorkflow;
import com.atlassian.jira.workflow.JiraWorkflow;
import com.atlassian.jira.workflow.WorkflowManager;
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
			
			Collection<Status> statuses = workflow.getLinkedStatusObjects();
			
			Map<String, String> map = getStatusesId();
			
			for (Map.Entry<String, String> entry : map.entrySet()) {
				boolean temp = false;
				for(Iterator<Status> iterator = statuses.iterator(); iterator.hasNext(); ) {
					Status status = iterator.next();
					if(status.getName().equals(entry.getKey())) temp = true;
				}
				if(!temp) newWorkflow.addStatus(statusManager.getStatus(entry.getValue()));
			}
					

//		StatusCategoryManager statusCategoryManager = ComponentAccessor.getComponent(StatusCategoryManager.class);
		
//		newWorkflow.addTransition(AddTransitionParameters.builder().name("Sans").description("Skeleton").sourceStatusId("10000").targetStatusId("10001").build());	
		
		
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

	public Map<String, String> getStatusesId() {

		Map<String, String> map = new HashMap<String, String>();

		Collection<Status> statuses = statusManager.getStatuses();

		for (Iterator<Status> iterator = statuses.iterator(); iterator.hasNext();) {
			Status status = iterator.next();
			if(status.getName().equals("Closed") || status.getName().equals("Resolved")) continue;
			map.put(status.getName(), status.getId());
		}

		return map;
	}
}
