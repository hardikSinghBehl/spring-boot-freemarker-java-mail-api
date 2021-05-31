package com.hardik.hedwig.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JacksonStdImpl
public class UserCreationRequestDto {

	@Schema(description = "email-id of the user", required = true)
	@NotBlank(message = "email-id must not be empty")
	@Email(message = "please enter a valid email-id")
	@Size(max = 50, message = "email-id must not exceed 50 characters")
	private final String emailId;

	@Schema(description = "Fullname of the user", required = true)
	@NotBlank(message = "fullname must not be empty")
	@Size(max = 50, message = "fullname must not exceed 50 characters")
	private final String fullName;

}
