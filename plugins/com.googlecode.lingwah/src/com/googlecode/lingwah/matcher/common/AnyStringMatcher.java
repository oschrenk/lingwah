/**
 * 
 */
package com.googlecode.lingwah.matcher.common;


import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.matcher.TerminalMatcher;

/**
 * Matches anything of the given length
 * @author ted stockwell
 */
public class AnyStringMatcher extends TerminalMatcher
{
	private int _length;

	public AnyStringMatcher(int length)
	{
		_length= length;
	}

	@Override
	public void startMatching(MatchContext ctx, int start, MatchResults matchResults) {
		String input= ctx.getInput();
		if (input.length()-start < _length) { 
			matchResults.setError("Expected input of length "+_length);			
		}
		else
			matchResults.addMatch(start+_length);
	}
}