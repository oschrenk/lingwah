package com.googlecode.lingwah.parser.common;


import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.parser.TerminalParser;

/**
 * Matches both single quoted or double quoted strings
 * 
 * @author Ted Stockwell
 */
public class QuotedStringParser
extends TerminalParser
{
	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults results)
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
