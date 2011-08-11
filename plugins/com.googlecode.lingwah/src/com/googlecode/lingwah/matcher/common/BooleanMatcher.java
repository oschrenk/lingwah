package com.googlecode.lingwah.matcher.common;


import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.matcher.TerminalMatcher;

public class BooleanMatcher
extends TerminalMatcher
{
	static final String T= "true";
	static final String F= "false";
	
	@Override
	public void startMatching(MatchContext ctx, int start, MatchResults results) 
	{
		String input= ctx.getInput();
		String s= null;
		if (start < input.length())
		{
			String string= input.substring(start);
			if (string.startsWith(T)) {
				s= T;
			}
			else if (string.startsWith(F))
				s= F;
		}

		if (s == null) { 
			results.setError("Expected 'true' or 'false'");
		}
		else
			results.addMatch(start+s.length());
	}
	
	@Override
	public String getDefaultLabel()
	{
		return "boolean";
	}
}
