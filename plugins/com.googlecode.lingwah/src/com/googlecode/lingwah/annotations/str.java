package com.googlecode.lingwah.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The associated element matches the given string value
 * @author ted stockwell
 */
@MatcherDefinition
@Retention(RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.FIELD)
public @interface str {
	String value();
}
