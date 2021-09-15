package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // type means that we can apply this to a class, an enum, or an interface.
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
	
	String tableName();

}
