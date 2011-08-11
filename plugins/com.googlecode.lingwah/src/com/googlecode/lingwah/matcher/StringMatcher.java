/**
 * 
 */
package com.googlecode.lingwah.matcher;


import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;

public final class StringMatcher extends TerminalMatcher
{
	private final String _target;

	public StringMatcher(String target)
	{
		_target= target;
	}
	
	@Override
	public String getDefaultLabel() {
		return "string('"+_target+"')";
	}

	public String getTarget() {
		return _target;
	}

	@Override
	public void startMatching(MatchContext ctx, int start, MatchResults matchResults) {
		String input= ctx.getInput();
		int i= 0;
		int l= _target.length();
		int e= input.length();
		while (i < l && (start+i) < e && input.charAt(start + i) == _target.charAt(i))
			i++;
		if (i == l) { 
			matchResults.addMatch(start + l);
		}
		else
			matchResults.setError("Expected '"+_target+"'", start + i);
	}

	@Override
	public void completeMatching(MatchContext ctx, int start, MatchResults matchResults) {
		// nothign to do
		
	}
}