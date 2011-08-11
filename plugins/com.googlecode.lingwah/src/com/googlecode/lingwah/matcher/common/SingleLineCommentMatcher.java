package com.googlecode.lingwah.matcher.common;


import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.matcher.TerminalMatcher;

/**
 * Matches everything from the beginning marker (passed in the constructor) 
 * to the end of the line. 
 * @author Ted Stockwell
 */
public class SingleLineCommentMatcher extends TerminalMatcher
{
	String _marker;
	public SingleLineCommentMatcher(String marker) {
		_marker= marker;
	}

	@Override
	public void startMatching(MatchContext ctx, int start, MatchResults results)
	{
		final String input= ctx.getInput();
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
