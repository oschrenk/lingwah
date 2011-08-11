package com.googlecode.lingwah.matcher.common;


import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.matcher.TerminalMatcher;

/**
 * Matches everything from a beginning marker to an ending marker.
 * Markers may NOT be nested. 
 *
 * @author Ted Stockwell
 */
public class SimpleBlockMatcher extends TerminalMatcher
{
	String _beginMarker;
	String _endMarker;
	
	public SimpleBlockMatcher(String beginMarker, String endMarker) {
		_beginMarker= beginMarker;
		_endMarker= endMarker;		
	}
	
	@Override
	public void startMatching(MatchContext ctx, int start, MatchResults results)
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
