/**
 * 
 */
package com.googlecode.lingwah.parser;


import java.util.Collection;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseError;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.node.Match;

/**
 * Like ChoiceParser, except it returns only the first match.
 * @author ted stockwell
 *
 */
public class FirstParser extends ChoiceParser
{
	public FirstParser(Parser[] matchers)
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

	@Override
	public void startMatching(final ParseContext ctx, final int start, final ParseResults targetResults) {
		final ResultsInfo info= new ResultsInfo();
		
		ParseResults.Listener listener= new ParseResults.Listener() {
			@Override
			public void onMatchFound(ParseResults results, Match node) {
				// only add the first match
				if (results.getMatches().isEmpty())
					targetResults.addMatch(node);
			}

			@Override
			public void onMatchError(ParseResults results, ParseError parseError) {
				if (info.results.size() <= 1) {
					// if only a single result set is wrapped then we can set the 
					// error immediately
					targetResults.setError(parseError); 
				}
				else
					info.errors.add(parseError);
			}
		};
		
		for (int i= 0; i < parsers.size(); i++)  {
			ParseResults m= ctx.doMatch(parsers.get(i), start);
			m.addListener(listener);
		}
	}

}