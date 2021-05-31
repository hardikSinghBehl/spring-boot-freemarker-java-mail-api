package com.hardik.hedwig.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.hedwig.constant.ApiConstant;
import com.hardik.hedwig.dto.UserCreationRequestDto;
import com.hardik.hedwig.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = ApiConstant.BASE_USER_PATH)
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ApiConstant.ACCOUNT_CREATION_SUCCESS_SUMMARY),
			@ApiResponse(responseCode = "403", description = ApiConstant.EMAIL_ID_ALREADY_EXISTS) })
	@Operation(summary = ApiConstant.ACCOUNT_CREATION_SUMMARY)
	public ResponseEntity<?> userCreationHandler(
			@Valid @RequestBody(required = true) final UserCreationRequestDto userCreationRequestDto) {
		return userService.create(userCreationRequestDto);
	}

}
