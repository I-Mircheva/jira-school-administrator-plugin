package com.atlassian.jira.school.impl;

import java.util.List;

import com.atlassian.jira.bc.project.ProjectCreationData;
import com.atlassian.jira.bc.project.ProjectCreationData.Builder;
import com.atlassian.jira.bc.project.ProjectService;
import com.atlassian.jira.bc.project.ProjectService.CreateProjectValidationResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.StatusManager;
import com.atlassian.jira.issue.status.category.StatusCategory;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.workflow.ConfigurableJiraWorkflow;
import com.atlassian.jira.workflow.JiraWorkflow;
import com.atlassian.jira.workflow.WorkflowManager;
import com.atlassian.jira.workflow.edit.Workflow;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;


@Scanned
public class WorkflowCreation {

	private ProjectManager projectManager;
	private ProjectService projectService;
	private WorkflowManager workflowManager;
	private StatusManager statusManager;
	private JiraAuthenticationContext authentication;
	private ApplicationUser user;
	
	public WorkflowCreation() {
		this.projectManager = ComponentAccessor.getProjectManager();
		this.projectService = ComponentAccessor.getComponent(ProjectService.class);
		this.workflowManager = ComponentAccessor.getWorkflowManager();
		this.statusManager = ComponentAccessor.getComponent(StatusManager.class);
		this.authentication = ComponentAccessor.getJiraAuthenticationContext();
		this.user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
	}
	
	public void createHomeworkWorkflow() {

		// New project creation - not needed now
//		ProjectCreationData data = new ProjectCreationData.Builder().withKey("MAX").withName("MAXX2423")
//				.withLead(user).withDescription("helloworld").withType("business").build();
//		
//		CreateProjectValidationResult projectResult = projectService.validateCreateProject(user, data);
//		projectService.createProject(projectResult);
		
		ConfigurableJiraWorkflow confWorkflow = new ConfigurableJiraWorkflow("Homework Workflow", workflowManager);
		confWorkflow.setDescription("Test workflow!?");
		
		workflowManager.createWorkflow(user, confWorkflow);
		
//		JiraWorkflow workflow = workflowManager.createDraftWorkflow(user, "Homework Workflow"); //projectManager.getProjectObjects().get(0).getKey() + ": Project Management Workflow");
//		
//		workflowManager.updateWorkflowNameAndDescription(user, workflow, "Homework: Workflow", "Trying to create custom workflow");
//				
//		Workflow newWorkflow = new Workflow(workflow, null, authentication, null, null, null);
//		
//		newWorkflow.addStatus(statusManager.createStatus("Аssigned", "A homework is assigned to a student", StatusCategory.UNDEFINED));
		
//		List<Status> list = workflow.getLinkedStatusObjects();
//		
//		for(int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i).getName() + " " + list.get(i).getSimpleStatus().getId());
//			StepDescriptor sd = workflow.getLinkedStep(list.get(i));
//			System.out.println("	step: " + sd.getName() + " " + sd.getId());
//		}

//		TranslationManager translationManager = ComponentAccessor.getTranslationManager();
//		StatusCategoryManager statusCategoryManager = ComponentAccessor.getComponent(StatusCategoryManager.class);
		
//		newWorkflow.addTransition(AddTransitionParameters.builder().name("Sans").description("Skeleton").sourceStatusId("10000").targetStatusId("10001").build());	
		
		
//		workflowManager.updateWorkflow(user, workflow);
//		workflowManager.overwriteActiveWorkflow(user, projectManager.getProjectObjects().get(0).getKey() + ": Project Management Workflow");
//		workflowManager.deleteDraftWorkflow(projectManager.getProjectObjects().get(0).getKey() + ": Project Management Workflow");
//		
//		workflow = workflowManager.getWorkflow(projectManager.getProjectObjects().get(0).getKey() + ": Project Management Workflow");
//		
//		workflowManager.updateWorkflowNameAndDescription(user, workflow, "Homework: Workflow", "Trying to create custom workflow");
//		
	}
	
	public void createStatus() {
		
		
	}
	
}
