package com.googlecode.lingwah.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The associated element matches one of the given matchers
 * @author ted stockwell
 */
@MatcherDefinition
@Retention(RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.FIELD)
public @interface rep {
	MatcherDefinition value();
	int max() default Integer.MAX_VALUE;
	int min() default 0;
}
