package com.example.rht.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", OffsetDateTime.now().toString());
		body.put("status", 400);
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
		body.put("errors", errors);
		return ResponseEntity.badRequest().body(body);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleConflict(IllegalStateException ex) {
		Map<String, Object> body = Map.of("timestamp", OffsetDateTime.now().toString(), "status", 409, "error",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleNotFound(NotFoundException ex) {
		Map<String, Object> body = Map.of("timestamp", OffsetDateTime.now().toString(), "status", 404, "error",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}
}