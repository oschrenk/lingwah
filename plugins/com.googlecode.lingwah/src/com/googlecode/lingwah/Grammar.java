package com.googlecode.lingwah;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.googlecode.lingwah.matcher.ChoiceMatcher;
import com.googlecode.lingwah.matcher.FirstMatcher;
import com.googlecode.lingwah.matcher.MutableMatcher;
import com.googlecode.lingwah.matcher.StringMatcher;

public class Grammar {
	
	protected Grammar() { 
	}
	
	final protected MutableMatcher define(final Matcher matcher)
	{
		return Parsers.define(matcher);
	}
	
	protected StringMatcher string(final String string)
	{
		return Parsers.string(string);
	}
	final protected StringMatcher str(final String string)
	{
		return string(string);
	}
	protected StringMatcher string(final char c)
	{
		return Parsers.string(c);
	}
	final protected StringMatcher str(final char c)
	{
		return string(c);
	}

	protected Matcher range(final char from, final char to)
	{
		return Parsers.range(from, to);
	}

	protected Matcher sequence(final Matcher... matchers)
	{
		return Parsers.sequence(matchers);
	}
	final protected Matcher seq(final Matcher... matchers)
	{
		return sequence(matchers);
	}

	protected ChoiceMatcher choice(final Matcher... matchers)
	{
		return Parsers.cho(matchers);
	}
	final protected ChoiceMatcher cho(final Matcher... matchers)
	{
		return cho(matchers);
	}
	
	protected Matcher anyChar()
	{
		return Parsers.anyChar();
	}
	
	protected FirstMatcher first(final Matcher... matchers)
	{
		return Parsers.first(matchers);
	}
	
	protected Matcher excluding(final Matcher matcher, final Matcher... filters)
	{
		return Parsers.excluding(matcher, filters);
	}
	final protected Matcher exc(final Matcher matcher, final Matcher... filters)
	{
		return excluding(matcher, filters);
	}

	protected Matcher repeat(final Matcher matcher)
	{
		return Parsers.repeat(matcher);
	}
	final protected Matcher rep(final Matcher matcher)
	{
		return repeat(matcher);
	}
	final protected Matcher oneOrMore(final Matcher matcher)
	{
		return Parsers.oneOrMore(matcher);
	}
	final protected Matcher zeroOrMore(final Matcher matcher)
	{
		return Parsers.zeroOrMore(matcher);
	}

	// ============================================================================================
	// === OPTIONAL MATCHERS
	// ============================================================================================

	protected Matcher optional(final Matcher matcher)
	{
		return Parsers.optional(matcher);
	}
	final protected Matcher opt(final Matcher matcher)
	{
		return optional(matcher);
	}

	protected <T> Matcher[] tail(final Matcher[] array)
	{
		return Arrays.asList(array).subList(1, array.length).toArray(new Matcher[] {});
	}
	
	
	protected void init() {
		generateElementLabels();
	}
	
	private void generateElementLabels() {
		Class<? extends Grammar> grammarClass= getClass();
		Field[] fields= grammarClass.getFields();
		for (Field field:fields) {
			if (!Matcher.class.isAssignableFrom(field.getType()))
				continue;
			field.setAccessible(true);
			Matcher matcher= null;
			try { 
				matcher= (Matcher)field.get(this);
			}
			catch (Exception x) {
				x.printStackTrace();
				continue;
			}
			matcher.setLabel(field.getName());
		}
		
	}
	
	
}
