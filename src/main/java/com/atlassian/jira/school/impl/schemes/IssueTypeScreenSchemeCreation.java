package com.atlassian.jira.school.impl.schemes;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.ConstantsManager;
import com.atlassian.jira.config.IssueTypeManager;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.context.GlobalIssueContext;
import com.atlassian.jira.issue.context.JiraContextNode;
import com.atlassian.jira.issue.customfields.CustomFieldSearcher;
import com.atlassian.jira.issue.customfields.CustomFieldType;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.FieldManager;
import com.atlassian.jira.issue.fields.OrderableField;
import com.atlassian.jira.issue.fields.screen.*;
import com.atlassian.jira.issue.fields.screen.FieldScreenImpl;
import com.atlassian.jira.issue.fields.screen.issuetype.*;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.issue.operation.IssueOperations;
import com.atlassian.jira.project.Project;
import org.ofbiz.core.entity.GenericEntityException;
import org.ofbiz.core.entity.GenericValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class IssueTypeScreenSchemeCreation {

	private IssueTypeScreenSchemeManager issueTypeScreenSchemeManager;

	// component dependencies, get via Constructor Injection
	private FieldScreenManager fieldScreenManager;
	private FieldScreenSchemeManager fieldScreenSchemeManager;
	private FieldManager fieldManager;
	private ConstantsManager constantsManager;
	private CustomFieldManager customFieldManager;
	private IssueTypeManager issueTypeManager;


	public IssueTypeScreenSchemeCreation() {
		this.issueTypeScreenSchemeManager = ComponentAccessor.getIssueTypeScreenSchemeManager();
		this.fieldScreenManager = ComponentAccessor.getFieldScreenManager();
		this.fieldScreenSchemeManager = ComponentAccessor.getComponent(FieldScreenSchemeManager.class);
		this.fieldManager = ComponentAccessor.getFieldManager();
		this.constantsManager = ComponentAccessor.getConstantsManager();
		this.customFieldManager = ComponentAccessor.getCustomFieldManager();
		this.issueTypeManager = ComponentAccessor.getComponent(IssueTypeManager.class);
	}

	public void createIssueTypeScreenScheme() throws GenericEntityException {

		Collection<IssueTypeScreenScheme> allIssueTypeScreenManager = issueTypeScreenSchemeManager.getIssueTypeScreenSchemes();

		for(IssueTypeScreenScheme tmp : allIssueTypeScreenManager) {
			if(tmp.getName().equals("Teacher Issue Type Screen Scheme")) {
				return;
			}
		}

		// create screen
		FieldScreen screenCreateIssueType = new FieldScreenImpl(fieldScreenManager);
		screenCreateIssueType.setName("Custom Teacher Screen");
		screenCreateIssueType.setDescription("A screen for issue");
		fieldScreenManager.createFieldScreen(screenCreateIssueType);


		// create tab
		FieldScreenTab myTab = screenCreateIssueType.addTab("Tab");

		// Create custom field
		CustomFieldType textFieldType = customFieldManager.getCustomFieldType("com.atlassian.jira.school.jira-school-plugin:teachertextfield");
		CustomFieldSearcher textFieldSearcher = customFieldManager.getCustomFieldSearcher("com.atlassian.jira.school.jira-school-plugin:teachertextfieldsearcher");
		List<JiraContextNode> contexts = new ArrayList<>();
		contexts.add(GlobalIssueContext.getInstance());

		//Create a list of issue types for which the custom field needs to be available
		List<IssueType> issueTypes = new ArrayList<>();
		issueTypes.add(null);


		customFieldManager.createCustomField("Teacher", "Teachers Field", textFieldType,
				textFieldSearcher,
				contexts,
				issueTypes);

		Collection<CustomField> allTeachersCustomFields =  customFieldManager.getCustomFieldObjectsByName("Teacher");
		String customFieldID = null;
		// Get last created teacher cf
		for (CustomField tmp : allTeachersCustomFields){
			customFieldID = tmp.getId();
		}

		OrderableField fieldTeacher = fieldManager.getCustomField(customFieldID); // e.g. "customfield_ID!"
		OrderableField fieldSummary = fieldManager.getOrderableField("summary"); // e.g. "customfield_ID!"
		myTab.addFieldScreenLayoutItem(fieldTeacher.getId());
		myTab.addFieldScreenLayoutItem(fieldSummary.getId());


		// add screen to scheme

		// get existing scheme... NO NEED
		//		FieldScreenScheme myScheme = fieldScreenSchemeManager.getFieldScreenScheme(mySchemeId);
		//		fieldScreenSchemeManager.

		// Create new scheme

		FieldScreenScheme myScheme = new FieldScreenSchemeImpl(fieldScreenSchemeManager);
		myScheme.setName("Teacher scheme");
		myScheme.setDescription("A teacher scheme description");
		fieldScreenSchemeManager.createFieldScreenScheme(myScheme);

		// add screen

		FieldScreenSchemeItem mySchemeItemCreate = new FieldScreenSchemeItemImpl(fieldScreenSchemeManager, fieldScreenManager);
		mySchemeItemCreate.setIssueOperation(IssueOperations.CREATE_ISSUE_OPERATION); // or: EDIT_ISSUE_OPERATION, VIEW_ISSUE_OPERATION
		mySchemeItemCreate.setFieldScreen(screenCreateIssueType);
		myScheme.addFieldScreenSchemeItem(mySchemeItemCreate);

		FieldScreenSchemeItem mySchemeItemEdit = new FieldScreenSchemeItemImpl(fieldScreenSchemeManager, fieldScreenManager);
		mySchemeItemEdit.setIssueOperation(IssueOperations.EDIT_ISSUE_OPERATION); // or: EDIT_ISSUE_OPERATION, VIEW_ISSUE_OPERATION
		mySchemeItemEdit.setFieldScreen(screenCreateIssueType);
		myScheme.addFieldScreenSchemeItem(mySchemeItemEdit);

		FieldScreenSchemeItem mySchemeItemView = new FieldScreenSchemeItemImpl(fieldScreenSchemeManager, fieldScreenManager);
		mySchemeItemView.setIssueOperation(IssueOperations.VIEW_ISSUE_OPERATION); // or: EDIT_ISSUE_OPERATION, VIEW_ISSUE_OPERATION
		mySchemeItemView.setFieldScreen(screenCreateIssueType);
		myScheme.addFieldScreenSchemeItem(mySchemeItemView);


		// create issueTypeScreenScheme
		IssueTypeScreenScheme myIssueTypeScreenScheme = new IssueTypeScreenSchemeImpl(issueTypeScreenSchemeManager);
		myIssueTypeScreenScheme.setName("Teacher Issue Type Screen Scheme");
		myIssueTypeScreenScheme.setDescription("Teacher Issue Type Screen Scheme Description");
		issueTypeScreenSchemeManager.createIssueTypeScreenScheme(myIssueTypeScreenScheme);

		// add scheme to issueTypeScreenScheme
		IssueTypeScreenSchemeEntity myEntity = null;

		Collection<IssueType> coll = issueTypeManager.getIssueTypes();

		for(IssueType tmp : coll) {
			System.out.println(tmp.getName());
			if(tmp.getName().equals("Subject")) {
				addEntity(myScheme, myIssueTypeScreenScheme, myEntity, tmp);
			} else if(tmp.getName().equals("Lecture")) {
				addEntity(myScheme, myIssueTypeScreenScheme, myEntity, tmp);
			} else if(tmp.getName().equals("Homework")) {
				addEntity(myScheme, myIssueTypeScreenScheme, myEntity, tmp);
			}
		}

		//      // assign to project
		//      // TODO Which projects
		//
		//      Project project = ComponentAccessor.getProjectManager().getProjects().get(0);
		//
		//      if(project != null) {
		//          issueTypeScreenSchemeManager.addSchemeAssociation(project, myIssueTypeScreenScheme);
		//      }

	}

	private void addEntity(FieldScreenScheme myScheme, IssueTypeScreenScheme myIssueTypeScreenScheme, IssueTypeScreenSchemeEntity myEntity, IssueType issueType) {
		myEntity = new IssueTypeScreenSchemeEntityImpl(issueTypeScreenSchemeManager,
				fieldScreenSchemeManager, constantsManager);
		myEntity.setIssueTypeId(issueType != null ? issueType.getId() : null); // an entity can be for all IssueTypes (-&gt; null), or just for 1
		myEntity.setFieldScreenScheme(myScheme);
		myIssueTypeScreenScheme.addEntity(myEntity);
	}
}

