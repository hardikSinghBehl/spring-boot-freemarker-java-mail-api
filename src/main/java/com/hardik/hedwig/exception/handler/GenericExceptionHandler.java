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

@ControllerAdvice
public class GenericExceptionHandler {

	@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> genericExceptionHandler(Exception exception) {
		final var response = new JSONObject();
		response.put(ApiConstant.STATUS, ApiConstant.FAILURE_STATUS);
		response.put(ApiConstant.MESSAGE, ApiConstant.GENERIC_FAILURE);
		response.put(ApiConstant.TIMESTAMP, LocalDateTime.now().toString());
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response.toString());
	}

}
