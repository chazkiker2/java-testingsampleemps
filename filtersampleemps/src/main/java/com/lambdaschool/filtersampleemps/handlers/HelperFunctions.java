package com.lambdaschool.filtersampleemps.handlers;


import com.lambdaschool.filtersampleemps.models.ValidationError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;


@Component
public class HelperFunctions {

	public List<ValidationError> getConstraintViolation(Throwable cause) {
		while ((cause != null) &&
		       !(cause instanceof ConstraintViolationException || cause instanceof MethodArgumentNotValidException)) {
			//			System.out.println(cause.getClass().toString());
			cause = cause.getCause();
		}
		List<ValidationError> validationErrors = new ArrayList<>();

		if (cause != null) {
			if (cause instanceof ConstraintViolationException) {
				ConstraintViolationException ex = (ConstraintViolationException) cause;
				ValidationError newValidationError = new ValidationError();
				newValidationError.setCode(ex.getMessage());
				newValidationError.setMessage(ex.getConstraintName());
				validationErrors.add(newValidationError);
			} else {
				// cause instanceof MethodArgumentNotValidException
				MethodArgumentNotValidException ex          = (MethodArgumentNotValidException) cause;
				List<FieldError>                fieldErrors = ex.getBindingResult()
						.getFieldErrors();
				for (FieldError err : fieldErrors) {
					ValidationError newValidationError = new ValidationError();
					newValidationError.setCode(err.getField());
					newValidationError.setMessage(err.getDefaultMessage());
					validationErrors.add(newValidationError);
				}
			}
		}
		return validationErrors;
	}

}
