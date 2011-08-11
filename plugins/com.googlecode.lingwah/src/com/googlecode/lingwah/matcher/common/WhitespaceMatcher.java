package com.googlecode.lingwah.matcher.common;


import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.matcher.TerminalMatcher;

/**
 * Matches the longest string of characters for which Character.isWhitespace 
 * returns true. 
 * @author Ted Stockwell
 */
public class WhitespaceMatcher extends TerminalMatcher
{
	@Override
	public void startMatching(MatchContext ctx, int start, MatchResults results)
	{
		final String input= ctx.getInput();
		int len= input.length();
		int i= start;
		while (i < len && Character.isWhitespace(input.charAt(i)))
			i++;
		if (i <= start) {
			results.setError("Expected whitespace");
			return;
		}
		results.addMatch(i);
	}
	
	@Override
	public String getDefaultLabel()
	{
		return "whitespace";
	}
}
