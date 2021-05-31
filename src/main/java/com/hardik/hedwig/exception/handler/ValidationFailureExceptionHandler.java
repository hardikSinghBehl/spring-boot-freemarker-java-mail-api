package com.hardik.hedwig.exception.handler;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONObject;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hardik.hedwig.constant.ApiConstant;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ValidationFailureExceptionHandler {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		String errorMessage = fieldErrors.get(0).getDefaultMessage();

		final var response = new JSONObject();
		response.put(ApiConstant.STATUS, ApiConstant.FAILURE_STATUS);
		response.put(ApiConstant.MESSAGE, errorMessage);
		response.put(ApiConstant.TIMESTAMP, LocalDateTime.now().toString());
		return ResponseEntity.badRequest().body(response.toString());
	}

}