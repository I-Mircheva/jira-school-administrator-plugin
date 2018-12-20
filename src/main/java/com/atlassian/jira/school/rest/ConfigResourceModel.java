package com.atlassian.jira.school.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigResourceModel {

	@XmlElement(name = "value")
	private String message;

	public ConfigResourceModel() {
	}

	public ConfigResourceModel(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}