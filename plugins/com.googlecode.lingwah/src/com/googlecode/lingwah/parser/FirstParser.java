/**
 * 
 */
package com.googlecode.lingwah.parser;


import java.util.ArrayList;
import java.util.Collection;

import com.googlecode.lingwah.Match;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseError;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;

/**
 * Like ChoiceParser, except it returns only the first match.
 * @author ted stockwell
 *
 */
public class FirstParser extends ChoiceParser
{
	public FirstParser(Parser... matchers)
	{
		super(matchers);
	}
	public FirstParser(Collection<Parser> parsers)
	{
		super(parsers);
	}
	
	@Override
	public String getDefaultLabel() {
		String label= "first(";
		boolean first= true;
		for (Parser parser:parsers) {
			if (!first)
				label+=", ";
			label+= parser.getLabel();
			first= false;
		}
		label+= ")";
		return label;
	}
	
	protected static class ResultsInfo {
		final ArrayList<ParseError> errors= new ArrayList<ParseError>();
	}
	
	
	static class FirstResults extends ParseResults {
		ParseResults _results;
		Parser _firstMatchingParser;
		public FirstResults(ParseResults results) {
			super(results.getContext(), results.getMatcher(), results.getPosition());
			_results= results;
		}
		
		public synchronized void addListener(Listener listener) {
			_results.addListener(listener);
		}
		
		@Override
		public synchronized void addMatch(Match match) {
			// if we already found a match then we're done
			if (_results.getMatches().isEmpty()) {
				_firstMatchingParser= match.getParser();
				_results.addMatch(match);
			}
			else {
				Match result= _results.getMatches().get(0);
				if (match.getStart() == result.getStart()) {
					if (match.getParser() == _firstMatchingParser) {
						_results.addMatch(match);
					}
				}
			}
		}
		public <T> void putMatcherInfo(Parser parser, T info) {
			_results.putMatcherInfo(parser, info);
		};
	}
	
	
	@Override
	public void startMatching(final ParseContext ctx, final int start, final ParseResults targetResults) {
		super.startMatching(ctx, start, new FirstResults(targetResults));
	}
	
}