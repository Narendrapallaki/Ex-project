package com.example.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.entity.ExceptionResult;



@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ExceptionResult> hl(CustomException ex) {

		ExceptionResult r1 = new ExceptionResult();

		r1.setResponse(ex.getMessage());
		r1.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
		return ResponseEntity.ok(r1);
	}

	

}
