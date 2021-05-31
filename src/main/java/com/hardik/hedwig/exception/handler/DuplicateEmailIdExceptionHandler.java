package com.hardik.hedwig.exception.handler;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hardik.hedwig.exception.DuplicateEmailIdException;

@ControllerAdvice
public class DuplicateEmailIdExceptionHandler {

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	@ExceptionHandler(DuplicateEmailIdException.class)
	public ResponseEntity<?> methodArgumentNotValidException(DuplicateEmailIdException exception) {
		final var response = new JSONObject();
		response.put("status", "Failure");
		response.put("message", "Account already exists with provided email-id");
		response.put("timestamp", LocalDateTime.now().toString());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response.toString());
	}

}
