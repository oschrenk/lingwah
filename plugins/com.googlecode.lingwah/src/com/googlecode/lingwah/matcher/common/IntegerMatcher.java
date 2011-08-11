package com.googlecode.lingwah.matcher.common;


import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.matcher.TerminalMatcher;

public class IntegerMatcher
extends TerminalMatcher
{
	@Override
	public void startMatching(MatchContext ctx, int start, MatchResults results) 
	{
		final String input= ctx.getInput();
		int i= start;
		if (start < input.length())
		{
			char c= input.charAt(start);
			int f= (c == '+' || c == '-') ? 1 : 0;
			
			if (Character.isDigit(input.charAt(f)))
			{
				i+= f + 1;
				while (Character.isDigit(input.charAt(i)))
					i++;
			}
		}
		
		if (i == start) { 
			results.setError("Expected an integer");
		}
		else
			results.addMatch(i);
	}
	
	@Override
	public String getDefaultLabel()
	{
		return "integer";
	}
}
