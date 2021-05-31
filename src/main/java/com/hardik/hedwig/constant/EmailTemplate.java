package com.hardik.hedwig.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailTemplate {

	USER_ACCOUNT_CREATION("account-creation-success", "Account Created Successfully!");

	private final String templateName;
	private final String subject;

}