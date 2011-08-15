/**
 * 
 */
package com.googlecode.lingwah.parser.common;


import com.googlecode.lingwah.Document;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.parser.TerminalParser;

public final class RangeParser extends TerminalParser
{
	private final char to;
	private final char from;

	public RangeParser(char to, char from)
	{
		this.to = to;
		this.from = from;
	}

	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults results)
	{
		Document input= ctx.getDocument();
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