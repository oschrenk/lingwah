/**
 * 
 */
package com.googlecode.lingwah.parser;


import java.util.Arrays;
import java.util.List;

import com.googlecode.lingwah.Match;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseError;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.Parsers;
import com.googlecode.lingwah.exception.RecursiveMatchersNotSupported;

/**
 * Wraps a parser and excludes any matches that match a given set of filters  
 * @author Ted Stockwell
 */
public final class ExcludingParser extends Parser
{
	private final Parser filterMatcher;
	private final Parser parser;

	public ExcludingParser(Parser parser, Parser[] filters)
	{
		this.filterMatcher = Parsers.choice(filters);
		this.parser= parser;
	}
	
	
	@Override
	public String getDefaultLabel() {
		return "excluding("+parser.getLabel()+", "+filterMatcher.getLabel()+")";
	}
	

	@Override
	public void startMatching(ParseContext ctx, int start, final ParseResults targetResults) {
		if (filterMatcher.isRecursive())
			throw new RecursiveMatchersNotSupported(this);
		
		final ParseResults filterResults= ctx.getParseResults(filterMatcher, start);
		
		ctx.doMatch(parser, start).addListener(new ParseResults.Listener() {
			@Override
			public void onMatchFound(ParseResults results, Match node) {
				String txt= node.getText();
				boolean exclude= false;
				for (Match filterNode:filterResults.getMatches()) {
					if (filterNode.getText().equals(txt)) {
						exclude= true;
						break;
					}
				}
				if (!exclude)
					targetResults.addMatch(node);
			}
			
			@Override
			public void onMatchError(ParseResults parseResults, ParseError _error) {
				targetResults.setError(_error);
			}
		});
	}

	@Override
	public void completeMatching(ParseContext ctx, int start, ParseResults parseResults) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Parser> getDependencies() {
		return Arrays.asList(new Parser[] { parser, filterMatcher} );
	}
}