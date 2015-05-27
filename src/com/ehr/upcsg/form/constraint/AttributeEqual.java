package com.ehr.upcsg.form.constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.ehr.upcsg.form.constraint.impl.AttributeEqualValidator;

@Constraint(validatedBy = AttributeEqualValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface AttributeEqual {

	String message() default "Fields must match";

	String first();

	String second();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	@interface List
    {
        AttributeEqual[] value();
    }
}
//code copied from onbcrm