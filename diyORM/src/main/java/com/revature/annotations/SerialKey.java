package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.FIELD) //meaning it can only be applied to the FIELDS of a class
@Retention(RetentionPolicy.RUNTIME)
public @interface SerialKey {
	
	String columnName();
	
}
