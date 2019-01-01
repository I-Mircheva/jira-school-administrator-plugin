package com.atlassian.jira.school.impl;

import java.util.List;

import org.ofbiz.core.entity.GenericValue;

import com.atlassian.jira.bc.project.ProjectService.GetProjectResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.StatusCategoryManager;
import com.atlassian.jira.entity.EntityUtils;
import com.atlassian.jira.issue.status.Status;
import com.atlassian.jira.issue.status.StatusImpl;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.BaseUrl;
import com.atlassian.jira.util.ofbiz.ImmutableGenericValue;
import com.atlassian.jira.web.action.admin.translation.TranslationManager;
import com.atlassian.jira.workflow.JiraWorkflow;
import com.atlassian.jira.workflow.WorkflowManager;
import com.atlassian.jira.workflow.edit.Workflow;
import com.atlassian.jira.workflow.edit.Workflow.AddTransitionParameters;
import com.atlassian.jira.workflow.edit.Workflow.AddTransitionParametersBuilder;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.opensymphony.workflow.loader.StepDescriptor;

import aQute.bnd.build.Project;

@Scanned
public class WorkflowCreation {

	private ProjectManager projectManager;
	private WorkflowManager workflowManager;
	private JiraAuthenticationContext authentication;
	private ApplicationUser user;
	
	public WorkflowCreation() {
		this.projectManager = ComponentAccessor.getProjectManager();
		this.workflowManager = ComponentAccessor.getWorkflowManager();
		this.authentication = ComponentAccessor.getJiraAuthenticationContext();
		this.user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
	}
	
	public void createHomeworkWorkflow() {

		JiraWorkflow workflow = workflowManager.getWorkflow(projectManager.getProjectObjects().get(0).getKey() + ": Task Management Workflow");
		workflowManager.updateWorkflowNameAndDescription(user, workflow, "Homework: Workflow", "Trying to create custom workflow");
		
		workflow = workflowManager.createDraftWorkflow(user, "Homework:Workflow");
				
		Workflow newWorkflow = new Workflow(workflow, null, authentication, null, null, null);
		
		List<Status> list = workflow.getLinkedStatusObjects();
		
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getName() + " " + list.get(i).getSimpleStatus().getId());
			StepDescriptor sd = workflow.getLinkedStep(list.get(i));
			System.out.println("	step: " + sd.getName() + " " + sd.getId());
		}

//		TranslationManager translationManager = ComponentAccessor.getTranslationManager();
//		StatusCategoryManager statusCategoryManager = ComponentAccessor.getComponent(StatusCategoryManager.class);
		
//		newWorkflow.addStatus(list.get(0));
//		newWorkflow.addStatus(new StatusImpl(??), translationManager, authentication, locator, statusCategoryManager));

//		newWorkflow.updateStatus("10004", "LovieLove");
		
//		newWorkflow.addTransition(AddTransitionParameters.builder().name("Sans").description("Skeleton").sourceStatusId("10000").targetStatusId("10001").build());	
		
//		workflowManager.updateWorkflow(user, workflow);
		
		workflowManager.overwriteActiveWorkflow(user, "Homework:Workflow");
		workflowManager.deleteDraftWorkflow("Homework:Workflow");
		
	}
	
}
