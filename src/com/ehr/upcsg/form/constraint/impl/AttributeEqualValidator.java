package com.ehr.upcsg.form.constraint.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

import com.ehr.upcsg.form.constraint.AttributeEqual;

public class AttributeEqualValidator implements ConstraintValidator<AttributeEqual, Object> {

	private String firstFieldName;
	private String secondFieldName;
	private String message;
	
	@Override
	public void initialize(final AttributeEqual constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		boolean result = true;
		try {
			final Object firstField = PropertyUtils.getProperty(value, firstFieldName);
			final Object secondField = PropertyUtils.getProperty(value, secondFieldName);
			result = firstField != null && firstField.equals(secondField);
		} catch (Exception e) {
			
		}
		if(result == false) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message)
				.addNode(secondFieldName)
				.addConstraintViolation();
		}
		return result;
	}

}

//code copied from onbcrm