package com.atlassian.jira.school.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ofbiz.core.entity.GenericEntityException;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueFieldConstants;
import com.atlassian.jira.issue.context.JiraContextNode;
import com.atlassian.jira.issue.customfields.CustomFieldUtils;
import com.atlassian.jira.issue.fields.FieldManager;
import com.atlassian.jira.issue.fields.config.FieldConfigScheme;
import com.atlassian.jira.issue.fields.config.manager.FieldConfigSchemeManager;
import com.atlassian.jira.issue.fields.config.manager.IssueTypeSchemeManager;
import com.atlassian.jira.issue.fields.screen.issuetype.IssueTypeScreenScheme;
import com.atlassian.jira.issue.fields.screen.issuetype.IssueTypeScreenSchemeManager;
import com.atlassian.jira.issue.security.IssueSecuritySchemeManager;
import com.atlassian.jira.issue.security.IssueSecuritySchemeService;
import com.atlassian.jira.permission.PermissionSchemeManager;
import com.atlassian.jira.permission.PermissionSchemeService;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.project.template.hook.AddProjectHook;
import com.atlassian.jira.project.template.hook.ConfigureData;
import com.atlassian.jira.project.template.hook.ConfigureResponse;
import com.atlassian.jira.project.template.hook.ValidateData;
import com.atlassian.jira.project.template.hook.ValidateResponse;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.workflow.AssignableWorkflowScheme;
import com.atlassian.jira.workflow.WorkflowSchemeManager;
import com.atlassian.jira.workflow.migration.AssignableWorkflowSchemeMigrationHelper;
import com.atlassian.jira.workflow.migration.MigrationHelperFactory;

public class AddProjectHookClass implements AddProjectHook {
	@Override
	public ValidateResponse validate(final ValidateData validateData) {
		ValidateResponse validateResponse = ValidateResponse.create();
		if (validateData.projectKey().equals("TEST")) {
			validateResponse.addErrorMessage("Invalid Project Key");
		}

		return validateResponse;
	}

	@Override
	public ConfigureResponse configure(final ConfigureData configureData) {
		ConfigureResponse configureResponse = ConfigureResponse.create();

		ApplicationUser applicationUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
		
		// assign workflow scheme

		Project project = configureData.project();
		ProjectManager projectManager = ComponentAccessor.getProjectManager();
		
		WorkflowSchemeManager workflowSchemeManager = ComponentAccessor.getWorkflowSchemeManager();
		AssignableWorkflowScheme workflowScheme = workflowSchemeManager.getWorkflowSchemeObj("Homework Workflow Scheme");
		MigrationHelperFactory migrationHelperFactory = ComponentAccessor
				.getComponent(MigrationHelperFactory.class);

		try {
			AssignableWorkflowSchemeMigrationHelper workflowSchemeMigrationHelper = migrationHelperFactory
					.createMigrationHelper(project, workflowScheme);
			workflowSchemeMigrationHelper.doQuickMigrate();
		} catch (GenericEntityException e) {
			e.printStackTrace();
		}

		// assign issue type scheme

		IssueTypeSchemeManager issueTypeSchemeManager = ComponentAccessor.getIssueTypeSchemeManager();
		FieldConfigSchemeManager fieldConfigSchemeManager = ComponentAccessor.getFieldConfigSchemeManager();
		FieldManager fieldManager = ComponentAccessor.getFieldManager();

		FieldConfigScheme issueTypeScheme = null;

		List<FieldConfigScheme> list = issueTypeSchemeManager.getAllSchemes();
		for (int i = 0; i < list.size(); i++) {
			FieldConfigScheme tempIssueTypeScheme = list.get(i);
			if (tempIssueTypeScheme.getName().equals("School")) issueTypeScheme = tempIssueTypeScheme;
		}

		List<Long> projectIds = new ArrayList<>(issueTypeScheme.getAssociatedProjectIds());
		projectIds.add(project.getId());

		List<JiraContextNode> contexts = CustomFieldUtils.buildJiraIssueContexts(false, projectIds.toArray(new Long[0]),
				projectManager);

		fieldConfigSchemeManager.updateFieldConfigScheme(issueTypeScheme, contexts,
				fieldManager.getConfigurableField(IssueFieldConstants.ISSUE_TYPE));
		
		// assign issue type screen scheme
		
		IssueTypeScreenSchemeManager issueTypeScreenSchemeManager = ComponentAccessor
				.getIssueTypeScreenSchemeManager();
				
		Collection<IssueTypeScreenScheme> coll = issueTypeScreenSchemeManager.getIssueTypeScreenSchemes();
		
		for(Iterator<IssueTypeScreenScheme> iterator = coll.iterator(); iterator.hasNext(); ) {
			IssueTypeScreenScheme screenScheme = (IssueTypeScreenScheme) iterator.next();
			if (screenScheme.getName().equals("Teacher Issue Type Screen Scheme")) {
				issueTypeScreenSchemeManager.addSchemeAssociation(project, screenScheme);
			}
		}
		
		// assign issue security scheme
		
		IssueSecuritySchemeManager issueSecuritySchemeManager = ComponentAccessor
				.getComponent(IssueSecuritySchemeManager.class);
				
		IssueSecuritySchemeService issueSecuritySchemeService = ComponentAccessor
				.getComponent(IssueSecuritySchemeService.class);
				
		Map<Long, Long> emptyMap = new HashMap<Long, Long>();
				
		List<Long> schemeIds = new ArrayList<>();
				
		schemeIds.add(issueSecuritySchemeManager.getSchemeObject("School Security Scheme").getId());
				
		issueSecuritySchemeService.assignSchemeToProject(applicationUser, projectIds.get(0), schemeIds.get(0), emptyMap);
		
				
		// assign permission scheme

		PermissionSchemeManager permissionSchemeManager = ComponentAccessor.getPermissionSchemeManager();
		PermissionSchemeService permissionSchemeService = ComponentAccessor.getComponent(PermissionSchemeService.class);

		schemeIds.add(permissionSchemeManager.getSchemeObject("School Permission Scheme").getId());

		permissionSchemeService.assignPermissionSchemeToProject(applicationUser, schemeIds.get(1), projectIds.get(0));

		return configureResponse;
		
	}
}