package com.googlecode.lingwah.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Associates a Parser with a syntax element name.
 * @author ted stockwell
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.FIELD)
public @interface match {
	String value();
}
