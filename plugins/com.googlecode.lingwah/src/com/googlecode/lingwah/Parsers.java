package com.googlecode.lingwah;


import com.googlecode.lingwah.parser.ChoiceParser;
import com.googlecode.lingwah.parser.ExcludingParser;
import com.googlecode.lingwah.parser.FirstParser;
import com.googlecode.lingwah.parser.MutableParser;
import com.googlecode.lingwah.parser.OptionalParser;
import com.googlecode.lingwah.parser.RegularExpressionParser;
import com.googlecode.lingwah.parser.RepetitionParser;
import com.googlecode.lingwah.parser.SequenceParser;
import com.googlecode.lingwah.parser.StringParser;
import com.googlecode.lingwah.parser.common.AnyStringParser;
import com.googlecode.lingwah.parser.common.RangeParser;

public final class Parsers
{
	public static final int max= Integer.MAX_VALUE;
	
	private Parsers()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	public static StringParser string(final String string)
	{
		return new StringParser(string);
	}
	public static final StringParser str(final String string)
	{
		return string(string);
	}
	public static final StringParser string(final char c)
	{
		return string(""+c);
	}
	public static final StringParser str(final char c)
	{
		return string(""+c);
	}

	public static Parser range(final char from, final char to)
	{
		return new RangeParser(to, from);
	}

	public static Parser sequence(final Parser... matchers)
	{
		return new SequenceParser(matchers);
	}
	public static final Parser seq(final Parser... matchers)
	{
		return sequence(matchers);
	}

	public static ChoiceParser choice(final Parser... matchers)
	{
		return new ChoiceParser(matchers);
	}
	public static final ChoiceParser cho(final Parser... matchers)
	{
		return choice(matchers);
	}
	
	
	public static FirstParser first(final Parser... matchers)
	{
		return new FirstParser(matchers);
	}
	
	public static Parser anyChar()
	{
		return new AnyStringParser(1);
	}
	
	public static Parser excluding(final Parser parser, final Parser... filters)
	{
		return new ExcludingParser(parser, filters);
	}
	public static final Parser exc(final Parser parser, final Parser... filters)
	{
		return excluding(parser, filters);
	}

	public static Parser repeat(final Parser parser)
	{
		return new RepetitionParser(parser);
	}
	public static final Parser rep(final Parser parser)
	{
		return repeat(parser);
	}
	public static final Parser oneOrMore(final Parser parser)
	{
		return repeat(parser);
	}
	public static final Parser zeroOrMore(final Parser parser)
	{
		return opt(repeat(parser));
	}
	

	// ============================================================================================
	// === OPTIONAL MATCHERS
	// ============================================================================================

	public static Parser optional(final Parser parser)
	{
		return new OptionalParser(parser);
	}
	final public static Parser opt(final Parser parser)
	{
		return optional(parser);
	}

	public static <T> Parser[] tail(final Parser[] array)
	{
		if (array == null)
			return null;
		Parser[] dest= new Parser[array.length-1];
		System.arraycopy(array, 1, dest, 0, dest.length);
		return dest;
	}

	public static MutableParser define(Parser parser) {
		return new MutableParser(parser);
	}

	public static Parser regex(String expression) {
		return new RegularExpressionParser(expression);
	}
}
