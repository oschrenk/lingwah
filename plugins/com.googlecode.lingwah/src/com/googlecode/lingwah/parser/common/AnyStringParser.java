/**
 * 
 */
package com.googlecode.lingwah.parser.common;


import com.googlecode.lingwah.Document;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.parser.TerminalParser;

/**
 * Matches anything of the given length
 * @author ted stockwell
 */
public class AnyStringParser extends TerminalParser
{
	private int _length;

	public AnyStringParser(int length)
	{
		_length= length;
	}

	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults parseResults) {
		Document input= ctx.getDocument();
		if (input.length()-start < _length) { 
			parseResults.setError("Expected input of length "+_length);			
		}
		else
			parseResults.addMatch(start+_length);
	}
}