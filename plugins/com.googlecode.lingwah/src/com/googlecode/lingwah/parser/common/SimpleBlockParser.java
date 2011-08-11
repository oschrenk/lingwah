package com.googlecode.lingwah.parser.common;


import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.parser.TerminalParser;

/**
 * Matches everything from a beginning marker to an ending marker.
 * Markers may NOT be nested. 
 *
 * @author Ted Stockwell
 */
public class SimpleBlockParser extends TerminalParser
{
	String _beginMarker;
	String _endMarker;
	
	public SimpleBlockParser(String beginMarker, String endMarker) {
		_beginMarker= beginMarker;
		_endMarker= endMarker;		
	}
	
	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults results)
	{
		final String input= ctx.getInput();
		if (input.startsWith(_beginMarker, start)) {
			int i= input.indexOf(_endMarker, start+_beginMarker.length());
			if (0 <= i) {
				int e= i+_endMarker.length();
				results.addMatch(e);
				return;
			}
			results.setError("Expected end of "+getLabel()+".");
			return;
		}
		results.setError("Expected "+getLabel()+".", start);
	}
	
	@Override
	public String getDefaultLabel()
	{
		return "simple block {beginMarker= "+_beginMarker+"; endMarker="+_endMarker+"}";
	}
}
