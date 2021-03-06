package com.hardik.hedwig.exception.handler;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hardik.hedwig.constant.ApiConstant;
import com.hardik.hedwig.exception.DuplicateEmailIdException;

@ControllerAdvice
public class DuplicateEmailIdExceptionHandler {

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	@ExceptionHandler(DuplicateEmailIdException.class)
	public ResponseEntity<?> duplicateEmailIdExceptionHandler(DuplicateEmailIdException exception) {
		final var response = new JSONObject();
		response.put(ApiConstant.STATUS, ApiConstant.FAILURE_STATUS);
		response.put(ApiConstant.MESSAGE, ApiConstant.EMAIL_ID_ALREADY_EXISTS);
		response.put(ApiConstant.TIMESTAMP, LocalDateTime.now().toString());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response.toString());
	}

}
