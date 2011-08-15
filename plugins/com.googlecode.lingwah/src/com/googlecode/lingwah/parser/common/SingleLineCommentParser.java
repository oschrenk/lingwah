package com.googlecode.lingwah.parser.common;


import com.googlecode.lingwah.Document;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.parser.TerminalParser;

/**
 * Matches everything from the beginning marker (passed in the constructor) 
 * to the end of the line. 
 * @author Ted Stockwell
 */
public class SingleLineCommentParser extends TerminalParser
{
	String _marker;
	public SingleLineCommentParser(String marker) {
		_marker= marker;
	}

	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults results)
	{
		final Document input= ctx.getDocument();
		if (input.startsWith(_marker, start)) {
			int len= input.length();
			int i= start+_marker.length();
			while (i < len && input.charAt(i) != '\n')
				i++;
			if (i < len)
				i++;
			if (i < len && input.charAt(i) == '\r')
				i++;
			results.addMatch(i);
			return;
		}
		results.setError("Expected whitespace");
	}

	@Override
	public String getDefaultLabel()
	{
		return "single line comment";
	}
}
