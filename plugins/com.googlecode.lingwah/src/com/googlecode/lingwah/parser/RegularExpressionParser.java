/**
 * 
 */
package com.googlecode.lingwah.parser;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseError;
import com.googlecode.lingwah.ParseResults;

public final class RegularExpressionParser extends TerminalParser
{
	private final String _target;
	private final Pattern _pattern;

	public RegularExpressionParser(String target)
	{
		 _pattern= Pattern.compile(target);
		_target= target;
	}
	
	@Override
	public String getDefaultLabel() {
		return "regex('"+_target+"')";
	}

	public String getTarget() {
		return _target;
	}

	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults parseResults) {
		Matcher m = _pattern.matcher(ctx.getInput().substring(start));
		if (!m.matches()) {
			parseResults.setError(new ParseError(this, "Expected "+_target, m.end()));
			return;
		}

		parseResults.addMatch(start + m.end());
	}

	@Override
	public void completeMatching(ParseContext ctx, int start, ParseResults parseResults) {
		// nothign to do
		
	}
}