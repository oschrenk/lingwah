package com.googlecode.lingwah.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation to associate MatchVistor methods with syntax elements.
 * Specifies the name of a syntax element.
 *  
 * @author Ted Stockwell
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.METHOD)
public @interface ElementType {
	 String value();
}
