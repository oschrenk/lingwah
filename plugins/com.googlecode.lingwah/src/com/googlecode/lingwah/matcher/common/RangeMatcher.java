/**
 * 
 */
package com.googlecode.lingwah.matcher.common;


import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.matcher.TerminalMatcher;

public final class RangeMatcher extends TerminalMatcher
{
	private final char to;
	private final char from;

	public RangeMatcher(char to, char from)
	{
		this.to = to;
		this.from = from;
	}

	@Override
	public void startMatching(MatchContext ctx, int start, MatchResults results)
	{
		String input= ctx.getInput();
		if (start < input.length()) 
		{
			char c= input.charAt(start);
			for (char ch = from; ch <= to; ch++)
			{
				if (c == ch) { 
					results.addMatch(start+1);
					return;
				}
			}
		}
		
		results.setError("Expected character in range {'"+from+"' to '"+to+"}");
	}
}