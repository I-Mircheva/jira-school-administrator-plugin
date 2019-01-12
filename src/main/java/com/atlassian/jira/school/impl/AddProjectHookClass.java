package com.atlassian.jira.school.impl;

import com.atlassian.jira.project.template.hook.AddProjectHook;
import com.atlassian.jira.project.template.hook.ConfigureData;
import com.atlassian.jira.project.template.hook.ConfigureResponse;
import com.atlassian.jira.project.template.hook.ValidateData;
import com.atlassian.jira.project.template.hook.ValidateResponse;

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

		return configureResponse;
	}
}