package com.googlecode.lingwah;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.googlecode.lingwah.parser.ChoiceParser;
import com.googlecode.lingwah.parser.FirstParser;
import com.googlecode.lingwah.parser.MutableParser;
import com.googlecode.lingwah.parser.StringParser;

public class Grammar {
	
	protected Grammar() { 
	}
	
	final protected MutableParser define(final Parser parser)
	{
		return Parsers.define(parser);
	}
	
	protected StringParser string(final String string)
	{
		return Parsers.string(string);
	}
	final protected StringParser str(final String string)
	{
		return string(string);
	}
	protected StringParser string(final char c)
	{
		return Parsers.string(c);
	}
	final protected StringParser str(final char c)
	{
		return string(c);
	}

	protected Parser range(final char from, final char to)
	{
		return Parsers.range(from, to);
	}

	protected Parser sequence(final Parser... matchers)
	{
		return Parsers.sequence(matchers);
	}
	final protected Parser seq(final Parser... matchers)
	{
		return sequence(matchers);
	}

	protected ChoiceParser choice(final Parser... matchers)
	{
		return Parsers.cho(matchers);
	}
	final protected ChoiceParser cho(final Parser... matchers)
	{
		return cho(matchers);
	}
	
	protected Parser anyChar()
	{
		return Parsers.anyChar();
	}
	
	protected FirstParser first(final Parser... matchers)
	{
		return Parsers.first(matchers);
	}
	
	protected Parser excluding(final Parser parser, final Parser... filters)
	{
		return Parsers.excluding(parser, filters);
	}
	final protected Parser exc(final Parser parser, final Parser... filters)
	{
		return excluding(parser, filters);
	}

	protected Parser repeat(final Parser parser)
	{
		return Parsers.repeat(parser);
	}
	final protected Parser rep(final Parser parser)
	{
		return repeat(parser);
	}
	final protected Parser oneOrMore(final Parser parser)
	{
		return Parsers.oneOrMore(parser);
	}
	final protected Parser zeroOrMore(final Parser parser)
	{
		return Parsers.zeroOrMore(parser);
	}

	// ============================================================================================
	// === OPTIONAL MATCHERS
	// ============================================================================================

	protected Parser optional(final Parser parser)
	{
		return Parsers.optional(parser);
	}
	final protected Parser opt(final Parser parser)
	{
		return optional(parser);
	}
	
	final protected Parser regex(String expression)
	{
		return Parsers.regex(expression);
	}

	protected <T> Parser[] tail(final Parser[] array)
	{
		return Arrays.asList(array).subList(1, array.length).toArray(new Parser[] {});
	}
	
	
	protected void init() {
		generateElementLabels();
	}
	
	private void generateElementLabels() {
		Class<? extends Grammar> grammarClass= getClass();
		Field[] fields= grammarClass.getFields();
		for (Field field:fields) {
			if (!Parser.class.isAssignableFrom(field.getType()))
				continue;
			field.setAccessible(true);
			Parser parser= null;
			try { 
				parser= (Parser)field.get(this);
			}
			catch (Exception x) {
				x.printStackTrace();
				continue;
			}
			parser.setLabel(field.getName());
		}
		
	}
	
	
}
