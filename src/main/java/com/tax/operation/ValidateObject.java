package com.tax.operation;

import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Stateless
public class ValidateObject {

	@Inject
	private Validator validator;

	public <T> void validateObject(T object) throws Exception {
		Set<ConstraintViolation<T>> violations = validator.validate(object);
		if (!violations.isEmpty())
			throw new Exception(violations.iterator().next().getMessage());
	}

}