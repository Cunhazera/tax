package com.tax.operation.test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tax.entity.Impost;
import com.tax.operation.ValidateObject;

public class ValidateObjectTest {

	@InjectMocks
	private ValidateObject validateImpost;
	@Mock
	private Validator validator;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test(expected = Exception.class)
	public void test() throws Exception {
		Impost impost = Impost.builder().id(1L).name("Impost").build();
		Set<ConstraintViolation<Impost>> violations = new HashSet<>();
		ConstraintViolation constraintViolation = Mockito.mock(ConstraintViolation.class);
		violations.add(constraintViolation);
		Mockito.when(constraintViolation.getMessage()).thenReturn("Error");
		Mockito.when(validator.validate(impost)).thenReturn(violations);
		validateImpost.validateObject(impost);
	}

	@Test
	public void testRight() throws Exception {
		Impost impost = Impost.builder().id(1L).name("Impost").percent(new BigDecimal(10)).build();
		validateImpost.validateObject(impost);
		Mockito.verify(validator).validate(impost);
	}

}
