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
	
	public ChoiceParser(Parser[] matchers)
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
	
	protected static class ResultsInfo {
		final ArrayList<ParseResults> results= new ArrayList<ParseResults>();
		final ArrayList<ParseError> errors= new ArrayList<ParseError>();
	}

	
	@Override
	public void startMatching(final ParseContext ctx, final int start, final ParseResults targetResults) {
		
		if (parsers.size() <= 0) { 
			targetResults.setError("This choice contains no options");
			return;
		}
		
		final ResultsInfo info= new ResultsInfo();
		targetResults.putMatcherInfo(this, info);
		
		ParseResults.Listener listener= new ParseResults.Listener() {
			@Override
			public void onMatchFound(ParseResults results, Match node) {
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
			info.results.add(m);
		}
	}
	
	@Override
	public void completeMatching(ParseContext ctx, int start, ParseResults parseResults) {
		final ResultsInfo info= parseResults.getMatcherInfo(this);
		/*
		 * If no results found then return the latest occurring error
		 * among all the choices. 
		 */
		if (parseResults.getMatches().isEmpty()) {
			ParseError error= null;
			for (ParseError e:info.errors) {
				if (error == null || error.position < e.position)
					error= e;
			}
			if (error != null)
				parseResults.setError(error);
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