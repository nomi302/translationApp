package com.translation.assessment.exception;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
	private int status;
	private String message;
	private List<String> errors;

	public ErrorResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}


	public ErrorResponse(int status, String message, List<String> errors) {
		this.status = status;
		this.message = message;
		this.errors = errors;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}