/**
 * 
 */
package com.googlecode.lingwah.matcher;


import java.util.Arrays;
import java.util.List;

import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchError;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.Matcher;
import com.googlecode.lingwah.Parsers;
import com.googlecode.lingwah.exception.RecursiveMatchersNotSupported;
import com.googlecode.lingwah.node.Match;

/**
 * Wraps a matcher and excludes any matches that match a given set of filters  
 * @author Ted Stockwell
 */
public final class ExcludingMatcher extends Matcher
{
	private final Matcher filterMatcher;
	private final Matcher matcher;

	public ExcludingMatcher(Matcher matcher, Matcher[] filters)
	{
		this.filterMatcher = Parsers.choice(filters);
		this.matcher= matcher;
	}
	
	
	@Override
	public String getDefaultLabel() {
		return "excluding("+matcher.getLabel()+", "+filterMatcher.getLabel()+")";
	}
	

	@Override
	public void startMatching(MatchContext ctx, int start, final MatchResults targetResults) {
		if (filterMatcher.isRecursive())
			throw new RecursiveMatchersNotSupported(this);
		
		final MatchResults filterResults= ctx.getMatchResults(filterMatcher, start);
		
		ctx.doMatch(matcher, start).addListener(new MatchResults.Listener() {
			@Override
			public void onMatchFound(MatchResults results, Match node) {
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
			public void onMatchError(MatchResults matchResults, MatchError _error) {
				targetResults.setError(_error);
			}
		});
	}

	@Override
	public void completeMatching(MatchContext ctx, int start, MatchResults matchResults) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Matcher> getDependencies() {
		return Arrays.asList(new Matcher[] { matcher, filterMatcher} );
	}
}