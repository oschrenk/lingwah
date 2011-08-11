package com.googlecode.lingwah;


import com.googlecode.lingwah.matcher.ChoiceMatcher;
import com.googlecode.lingwah.matcher.ExcludingMatcher;
import com.googlecode.lingwah.matcher.FirstMatcher;
import com.googlecode.lingwah.matcher.MutableMatcher;
import com.googlecode.lingwah.matcher.OptionalMatcher;
import com.googlecode.lingwah.matcher.RepetitionMatcher;
import com.googlecode.lingwah.matcher.SequenceMatcher;
import com.googlecode.lingwah.matcher.StringMatcher;
import com.googlecode.lingwah.matcher.common.AnyStringMatcher;
import com.googlecode.lingwah.matcher.common.RangeMatcher;

public final class Parsers
{
	public static final int max= Integer.MAX_VALUE;
	
	private Parsers()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	public static StringMatcher string(final String string)
	{
		return new StringMatcher(string);
	}
	public static final StringMatcher str(final String string)
	{
		return string(string);
	}
	public static final StringMatcher string(final char c)
	{
		return string(""+c);
	}
	public static final StringMatcher str(final char c)
	{
		return string(""+c);
	}

	public static Matcher range(final char from, final char to)
	{
		return new RangeMatcher(to, from);
	}

	public static Matcher sequence(final Matcher... matchers)
	{
		return new SequenceMatcher(matchers);
	}
	public static final Matcher seq(final Matcher... matchers)
	{
		return sequence(matchers);
	}

	public static ChoiceMatcher choice(final Matcher... matchers)
	{
		return new ChoiceMatcher(matchers);
	}
	public static final ChoiceMatcher cho(final Matcher... matchers)
	{
		return choice(matchers);
	}
	
	
	public static FirstMatcher first(final Matcher... matchers)
	{
		return new FirstMatcher(matchers);
	}
	
	public static Matcher anyChar()
	{
		return new AnyStringMatcher(1);
	}
	
	public static Matcher excluding(final Matcher matcher, final Matcher... filters)
	{
		return new ExcludingMatcher(matcher, filters);
	}
	public static final Matcher exc(final Matcher matcher, final Matcher... filters)
	{
		return excluding(matcher, filters);
	}

	public static Matcher repeat(final Matcher matcher)
	{
		return new RepetitionMatcher(matcher);
	}
	public static final Matcher rep(final Matcher matcher)
	{
		return repeat(matcher);
	}
	public static final Matcher oneOrMore(final Matcher matcher)
	{
		return repeat(matcher);
	}
	public static final Matcher zeroOrMore(final Matcher matcher)
	{
		return opt(repeat(matcher));
	}
	

	// ============================================================================================
	// === OPTIONAL MATCHERS
	// ============================================================================================

	public static Matcher optional(final Matcher matcher)
	{
		return new OptionalMatcher(matcher);
	}
	final public static Matcher opt(final Matcher matcher)
	{
		return optional(matcher);
	}

	public static <T> Matcher[] tail(final Matcher[] array)
	{
		if (array == null)
			return null;
		Matcher[] dest= new Matcher[array.length-1];
		System.arraycopy(array, 1, dest, 0, dest.length);
		return dest;
	}

	public static MutableMatcher define(Matcher matcher) {
		return new MutableMatcher(matcher);
	}
}
