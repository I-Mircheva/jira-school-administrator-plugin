package com.atlassian.jira.school.impl;

import java.util.ArrayList;
import java.util.List;

import org.ofbiz.core.entity.GenericEntityException;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueFieldConstants;
import com.atlassian.jira.issue.context.JiraContextNode;
import com.atlassian.jira.issue.customfields.CustomFieldUtils;
import com.atlassian.jira.issue.fields.FieldManager;
import com.atlassian.jira.issue.fields.config.FieldConfigScheme;
import com.atlassian.jira.issue.fields.config.manager.FieldConfigSchemeManager;
import com.atlassian.jira.issue.fields.config.manager.IssueTypeSchemeManager;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.project.template.hook.AddProjectHook;
import com.atlassian.jira.project.template.hook.ConfigureData;
import com.atlassian.jira.project.template.hook.ConfigureResponse;
import com.atlassian.jira.project.template.hook.ValidateData;
import com.atlassian.jira.project.template.hook.ValidateResponse;
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

		//assign workflow scheme

		Project project = configureData.project();

		WorkflowSchemeManager workflowSchemeManager = ComponentAccessor.getWorkflowSchemeManager();
		AssignableWorkflowScheme workflowScheme = workflowSchemeManager.getWorkflowSchemeObj("Workflow Scheme");

		MigrationHelperFactory migrationHelperFactory = ComponentAccessor
				.getComponent(MigrationHelperFactory.class);

		try {
			AssignableWorkflowSchemeMigrationHelper workflowSchemeMigrationHelper = migrationHelperFactory
					.createMigrationHelper(project, workflowScheme);
			workflowSchemeMigrationHelper.doQuickMigrate();
		} catch (GenericEntityException e) {
			e.printStackTrace();
		}

		//assign issue type scheme

		IssueTypeSchemeManager issueTypeSchemeManager = ComponentAccessor.getIssueTypeSchemeManager();
		FieldConfigSchemeManager schemeManager = ComponentAccessor.getFieldConfigSchemeManager();
		ProjectManager projectManager = ComponentAccessor.getProjectManager();
		FieldManager fieldManager = ComponentAccessor.getFieldManager();

		FieldConfigScheme issueTypeScheme1 = null;

		List<FieldConfigScheme> list = issueTypeSchemeManager.getAllSchemes();
		for(int i = 0; i < list.size(); i++) {
			FieldConfigScheme issueTypeScheme = list.get(i);
			if(issueTypeScheme.getName().equals("School")) issueTypeScheme1 = issueTypeScheme;
		}

	    List<Long> projectIds = new ArrayList<>(issueTypeScheme1.getAssociatedProjectIds());
	    projectIds.add(project.getId());

	    List<JiraContextNode> contexts = CustomFieldUtils.buildJiraIssueContexts(false,
	    		projectIds.toArray(new Long[0]), projectManager);

	    schemeManager.updateFieldConfigScheme(issueTypeScheme1, contexts,
	    		fieldManager.getConfigurableField(IssueFieldConstants.ISSUE_TYPE));

		return configureResponse;
	}
}