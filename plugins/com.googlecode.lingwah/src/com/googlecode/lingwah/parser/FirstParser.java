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
		public FirstResults(ParseContext ctx, Parser parser, int position) {
			super(ctx, parser, position);
		}

		Parser _firstMatchingParser;
		
		@Override
		public synchronized void addMatch(Match match) {
			if (getMatches().isEmpty()) {
				_firstMatchingParser= match.getParser();
				super.addMatch(match);
			}
			else {
				// there may be more that one error that occurs in the 
				// same spot by the same parser, save them all.
				// In essence, there may be a 'tie' for first result.
				Match result= getMatches().get(0);
				if (match.getStart() == result.getStart()) {
					if (match.getParser() == _firstMatchingParser) {
						super.addMatch(match);
					}
				}
			}
		}
	}
	
	@Override
	public ParseResults createResults(ParseContext parseContext, int start) {
		return new FirstResults(parseContext, this, start);
	}
	
}