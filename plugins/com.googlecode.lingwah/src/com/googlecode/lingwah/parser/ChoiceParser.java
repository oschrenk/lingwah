/**
 * 
 */
package com.googlecode.lingwah.parser;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.googlecode.lingwah.Match;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseError;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;

public class ChoiceParser extends Parser
{
	protected final ArrayList<Parser> parsers= new ArrayList<Parser>();
	
	/**
	 * Just like ParseResults but when saving errors this class 
	 * saves the error that occurs latest in the input instead of the 
	 * earliest occurring error. 
	 */
	public static class ChoiceResults extends ParseResults {

		public ChoiceResults(ParseContext ctx, Parser parser, int position) {
			super(ctx, parser, position);
		}
		
		@Override
		protected void saveError(ParseError error) {
			if (_error == null || _error.position < error.position) 
				_error= error;
		}
		
	}
	
	public ChoiceParser(Parser... matchers)
	{
		for (Parser parser:matchers)
			this.parsers.add(parser);
	}
	public ChoiceParser(Collection<Parser> parsers)
	{
		for (Parser parser:parsers)
			this.parsers.add(parser);
	}
	public ChoiceParser()
	{
	}
	
	@Override
	public ParseResults createResults(ParseContext parseContext, int start) {
		return new ChoiceResults(parseContext, this, start);
	}
	
	@Override
	public String getDefaultLabel() {
		String label= "choice(";
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
		
		if (parsers.size() <= 0) { 
			targetResults.setError("This choice contains no options");
			return;
		}
		
		for (int i= 0; i < parsers.size(); i++)  {
			ParseResults m= ctx.doMatch(parsers.get(i), start);
			m.addListener(new ParseResults.Listener() {
				@Override
				public void onMatchFound(ParseResults results, Match node) {
					targetResults.addMatch(node);
				}

				@Override
				public void onMatchError(ParseResults results, ParseError parseError) {
					targetResults.setError(parseError); 
				}
			});
		}
	}
	
	public void addChoice(Parser parser) {
		this.parsers.add(parser);
	}

	public void addChoices(Parser... matchers) {
		for (Parser parser:matchers)
			this.parsers.add(parser);
	}
	
	@Override
	public List<Parser> getDependencies() {
		return Collections.unmodifiableList(parsers);
	}
}