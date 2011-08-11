package com.googlecode.lingwah.parser.common;


import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.parser.TerminalParser;

public class IntegerParser
extends TerminalParser
{
	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults results) 
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
