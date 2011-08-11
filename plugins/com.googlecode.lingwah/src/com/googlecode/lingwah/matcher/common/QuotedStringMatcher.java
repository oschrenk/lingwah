package com.googlecode.lingwah.matcher.common;


import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.matcher.TerminalMatcher;

/**
 * Matches both single quoted or double quoted strings
 * 
 * @author Ted Stockwell
 */
public class QuotedStringMatcher
extends TerminalMatcher
{
	@Override
	public void startMatching(MatchContext ctx, int start, MatchResults results)
	{
		final String input= ctx.getInput();
		if (input.length() <= start) { 
			results.setError("Unexpected end of input");
			return;
		}
			
		char startChar= input.charAt(start);
		if (startChar != '"' && startChar != '\'') { 
			results.setError("Excepted beginning quote");
			return;
		}
		
		char escapeChar= (startChar == '"') ? '\'' : '"';
		int pos = input.indexOf(startChar, start+1);
		while (pos > 0 && input.charAt(pos - 1) == escapeChar)
			pos = input.indexOf(startChar, pos+1);

		if (pos == -1 || input.charAt(pos) != startChar) {  
			results.setError("Excepted end of quote", input.length());
			return;
		}
		pos++;

		results.addMatch(pos);
	}
	
	@Override
	public String getDefaultLabel()
	{
		return "quoted string";
	}

}
