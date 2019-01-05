package com.atlassian.jira.school.impl;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.ConstantsManager;
import com.atlassian.jira.issue.fields.Field;
import com.atlassian.jira.issue.fields.FieldManager;
import com.atlassian.jira.issue.fields.OrderableField;
import com.atlassian.jira.issue.fields.screen.*;
import com.atlassian.jira.issue.fields.screen.FieldScreenImpl;
import com.atlassian.jira.issue.fields.screen.issuetype.*;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.issue.operation.IssueOperations;
import com.atlassian.jira.project.Project;
import org.ofbiz.core.entity.GenericValue;


public class IssueTypeScreenSchemeCreation {

	private IssueTypeScreenSchemeManager issueTypeScreenSchemeManager;

	// component dependencies, get via Constructor Injection
	private FieldScreenManager fieldScreenManager;
	private FieldScreenSchemeManager fieldScreenSchemeManager;
	private FieldManager fieldManager;
	private ConstantsManager constantsManager;


	public IssueTypeScreenSchemeCreation() {
		this.issueTypeScreenSchemeManager = ComponentAccessor.getIssueTypeScreenSchemeManager();
		this.fieldScreenManager = ComponentAccessor.getFieldScreenManager();
		this.fieldScreenSchemeManager = ComponentAccessor.getComponent(FieldScreenSchemeManager.class);
		this.fieldManager = ComponentAccessor.getFieldManager();
		this.constantsManager = ComponentAccessor.getConstantsManager();
	}

	public void createIssueTypeScreenScheme() {

		// create screen
		FieldScreen screenCreateIssueType = new FieldScreenImpl(fieldScreenManager);
		screenCreateIssueType.setName("CustomScreen");
		screenCreateIssueType.setDescription("A screen create issue");
		fieldScreenManager.createFieldScreen(screenCreateIssueType);


		// create tab
		FieldScreenTab myTab = screenCreateIssueType.addTab("Tab");

		// add field to tab
		//		for (Field field : fieldManager.getAllSearchableFields()) {
		//			System.out.println("+=++++++++++++++++++++++++++++++++++++");
		//			System.out.println(field.getId());
		//			System.out.println(field.getName());
		//			System.out.println(field.getNameKey());
		//		}
		OrderableField fieldTeacher = fieldManager.getCustomField("customfield_10000"); // e.g. "customfield_ID!"
		OrderableField fieldSummary = fieldManager.getOrderableField("summary"); // e.g. "customfield_ID!"
		myTab.addFieldScreenLayoutItem(fieldTeacher.getId());
		myTab.addFieldScreenLayoutItem(fieldSummary.getId());


		// add screen to scheme

		// get existing scheme...
		//		FieldScreenScheme myScheme = fieldScreenSchemeManager.getFieldScreenScheme(mySchemeId);
		//		fieldScreenSchemeManager.

		// ... or create new scheme

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
		IssueTypeScreenSchemeEntity myEntity = new IssueTypeScreenSchemeEntityImpl(
				issueTypeScreenSchemeManager, (GenericValue) null, fieldScreenSchemeManager, constantsManager);

		// try and get issue type
		//		System.out.println("++++++++++++++++++++++++++++++++++++++++++");
		//		System.out.println("++++++++++++++++++++++++++++++++++++++++++");
		//		System.out.println("++++++++++++++++++++++++++++++++++++++++++");
		//		System.out.println((IssueType) ComponentAccessor.getConstantsManager().getAllIssueTypeObjects().toArray()[0]);
		//		System.out.println((IssueType) ComponentAccessor.getConstantsManager().getAllIssueTypeObjects().toArray()[1]);
		//		System.out.println((IssueType) ComponentAccessor.getConstantsManager().getAllIssueTypeObjects().toArray()[2]);
		//		System.out.println((IssueType) ComponentAccessor.getConstantsManager().getAllIssueTypeObjects().toArray()[3]);
		//		System.out.println((IssueType) ComponentAccessor.getConstantsManager().getAllIssueTypeObjects().toArray()[4]);
		//		System.out.println((IssueType) ComponentAccessor.getConstantsManager().getAllIssueTypeObjects().toArray()[5]);
		//		System.out.println("++++++++++++++++++++++++++++++++++++++++++");
		//		System.out.println("++++++++++++++++++++++++++++++++++++++++++");
		//		System.out.println("++++++++++++++++++++++++++++++++++++++++++");
		// TODO Fix magic numbers

		IssueType issueType1 = (IssueType) ComponentAccessor.getConstantsManager().getAllIssueTypeObjects().toArray()[0];
		IssueType issueType2 = (IssueType) ComponentAccessor.getConstantsManager().getAllIssueTypeObjects().toArray()[1];
		IssueType issueType3 = (IssueType) ComponentAccessor.getConstantsManager().getAllIssueTypeObjects().toArray()[2];
		IssueType issueType4 = (IssueType) ComponentAccessor.getConstantsManager().getAllIssueTypeObjects().toArray()[4];

		addEntity(myScheme, myIssueTypeScreenScheme, myEntity, issueType1);
		addEntity(myScheme, myIssueTypeScreenScheme, myEntity, issueType2);
		addEntity(myScheme, myIssueTypeScreenScheme, myEntity, issueType3);
		addEntity(myScheme, myIssueTypeScreenScheme, myEntity, issueType4);


		// assign to project
		// TODO Which projects
		Project project = ComponentAccessor.getProjectManager().getProjects().get(0);

		if(project != null) {
			issueTypeScreenSchemeManager.addSchemeAssociation(project, myIssueTypeScreenScheme);
		}
	}

	private void addEntity(FieldScreenScheme myScheme, IssueTypeScreenScheme myIssueTypeScreenScheme, IssueTypeScreenSchemeEntity myEntity, IssueType issueType) {
		myEntity.setIssueTypeId(issueType != null ? issueType.getId() : null); // an entity can be for all IssueTypes (-&gt; null), or just for 1
		myEntity.setFieldScreenScheme(myScheme);
		myIssueTypeScreenScheme.addEntity(myEntity);
	}
}

