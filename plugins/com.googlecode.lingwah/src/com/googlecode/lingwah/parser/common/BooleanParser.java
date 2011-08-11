package com.googlecode.lingwah.parser.common;


import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.parser.TerminalParser;

public class BooleanParser
extends TerminalParser
{
	static final String T= "true";
	static final String F= "false";
	
	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults results) 
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
