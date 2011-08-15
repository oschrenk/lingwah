package com.googlecode.lingwah.parser.common;


import com.googlecode.lingwah.Document;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.parser.TerminalParser;

/**
 * Matches the longest string of characters for which Character.isWhitespace 
 * returns true. 
 * @author Ted Stockwell
 */
public class WhitespaceParser extends TerminalParser
{
	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults results)
	{
		final Document input= ctx.getDocument();
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
