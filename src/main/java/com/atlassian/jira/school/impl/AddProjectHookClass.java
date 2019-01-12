package com.atlassian.jira.school.impl;

import org.ofbiz.core.entity.GenericEntityException;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.project.Project;
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

		return configureResponse;
	}
}