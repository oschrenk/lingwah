package com.googlecode.lingwah.parser.common;


import com.googlecode.lingwah.Document;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseError;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.ParseResults.Listener;
import com.googlecode.lingwah.node.Match;
import com.googlecode.lingwah.parser.TerminalParser;

public class DoubleParser
extends TerminalParser
{
	static final private IntegerParser __integerMatcher= new IntegerParser();
	//decimal ::= ([sign] ((digits "." {digits}) | ("." digits) | digits)) 
	@Override
	public void startMatching(final ParseContext ctx, final int start, final ParseResults targetResults)
	{
		final Document input= ctx.getDocument();
		ctx.doMatch(__integerMatcher, start).addListener( new Listener() {
			Match _match;
			@Override
			public void onMatchFound(ParseResults integerResults, Match node) {
				_match= node;
			}
			
			@Override
			public void onMatchError(ParseResults integerResults, ParseError parseError) {
				if (_match == null) {
					if ('.' == input.charAt(start))
					{
						ctx.doMatch(__integerMatcher, start+1).addListener(new Listener() {
							Match _fraction;
							@Override
							public void onMatchFound(ParseResults integerResults, Match node) {
								_fraction= node;
							}
							@Override
							public void onMatchError(ParseResults integerResults, ParseError parseError) {
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
							public void onMatchFound(ParseResults integerResults, Match node) {
								_fraction= node;
							}
							@Override
							public void onMatchError(ParseResults integerResults, ParseError parseError) {
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
