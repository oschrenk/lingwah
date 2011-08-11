package com.googlecode.lingwah.matcher.common;


import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchError;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.MatchResults.Listener;
import com.googlecode.lingwah.matcher.TerminalMatcher;
import com.googlecode.lingwah.node.Match;

public class DoubleMatcher
extends TerminalMatcher
{
	static final private IntegerMatcher __integerMatcher= new IntegerMatcher();
	//decimal ::= ([sign] ((digits "." {digits}) | ("." digits) | digits)) 
	@Override
	public void startMatching(final MatchContext ctx, final int start, final MatchResults targetResults)
	{
		final String input= ctx.getInput();
		ctx.doMatch(__integerMatcher, start).addListener( new Listener() {
			Match _match;
			@Override
			public void onMatchFound(MatchResults integerResults, Match node) {
				_match= node;
			}
			
			@Override
			public void onMatchError(MatchResults integerResults, MatchError matchError) {
				if (_match == null) {
					if ('.' == input.charAt(start))
					{
						ctx.doMatch(__integerMatcher, start+1).addListener(new Listener() {
							Match _fraction;
							@Override
							public void onMatchFound(MatchResults integerResults, Match node) {
								_fraction= node;
							}
							@Override
							public void onMatchError(MatchResults integerResults, MatchError matchError) {
								if (_fraction == null) {
									targetResults.setError("Expected a double");
									return;
								}
								targetResults.addMatch(_fraction.getEnd());
							}
						});
					}
					else
						targetResults.setError("Expected a double");
				}
				else {
					if ('.' == input.charAt(_match.getEnd())) 
					{
						ctx.doMatch(__integerMatcher, _match.getEnd()+1).addListener(new Listener() {
							Match _fraction;
							@Override
							public void onMatchFound(MatchResults integerResults, Match node) {
								_fraction= node;
							}
							@Override
							public void onMatchError(MatchResults integerResults, MatchError matchError) {
								int end= _match.getEnd()+1;
								if (_fraction != null)
									end= _fraction.getEnd();
								targetResults.addMatch(end);
							}
						});
					}
					else {
						targetResults.addMatch(_match);
					}
				}
			}
		});
	}
	
	@Override
	public String getDefaultLabel()
	{
		return "double";
	}
}
