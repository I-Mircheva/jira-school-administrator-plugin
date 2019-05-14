package com.atlassian.jira.school.customfields;

import com.atlassian.jira.issue.customfields.impl.GenericTextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.fields.TextFieldCharacterLengthValidator;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;

// This class is created following https://developer.atlassian.com/server/jira/platform/creating-a-custom-field-type

@Scanned
public class CustomFieldCreation extends GenericTextCFType {

	public CustomFieldCreation(
			@JiraImport CustomFieldValuePersister customFieldValuePersister,
			@JiraImport GenericConfigManager genericConfigManager,
			@JiraImport TextFieldCharacterLengthValidator textFieldCharacterLengthValidator,
			@JiraImport JiraAuthenticationContext jiraAuthenticationContext) {

		// The GenericTextCFType class stores and retrieves field values as strings which is used in the custom field functionality 

		super(customFieldValuePersister, genericConfigManager, textFieldCharacterLengthValidator , jiraAuthenticationContext);
	}
}
