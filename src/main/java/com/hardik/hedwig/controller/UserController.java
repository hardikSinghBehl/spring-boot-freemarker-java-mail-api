package com.hardik.hedwig.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.hedwig.dto.UserCreationRequestDto;
import com.hardik.hedwig.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Account created successfully"),
			@ApiResponse(responseCode = "403", description = "Account already exists with provided email-id") })
	@Operation(summary = "Creates a user account")
	public ResponseEntity<?> userCreationHandler(
			@Valid @RequestBody(required = true) final UserCreationRequestDto userCreationRequestDto) {
		return userService.create(userCreationRequestDto);
	}

}
