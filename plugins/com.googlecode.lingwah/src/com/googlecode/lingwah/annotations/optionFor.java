package com.googlecode.lingwah.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The given parser is an option for the named choice parser
 * @author ted stockwell
 */
@MatcherDefinition
@Retention(RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.FIELD)
public @interface optionFor {
	String value();
}
