/**
 * 
 */
package com.googlecode.lingwah.matcher;


import java.util.Collection;

import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchError;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.Matcher;
import com.googlecode.lingwah.node.Match;

/**
 * Like ChoiceMatcher, except it returns only the first match.
 * @author ted stockwell
 *
 */
public class FirstMatcher extends ChoiceMatcher
{
	public FirstMatcher(Matcher[] matchers)
	{
		super(matchers);
	}
	public FirstMatcher(Collection<Matcher> matchers)
	{
		super(matchers);
	}
	
	@Override
	public String getDefaultLabel() {
		String label= "first(";
		boolean first= true;
		for (Matcher matcher:matchers) {
			if (!first)
				label+=", ";
			label+= matcher.getLabel();
			first= false;
		}
		label+= ")";
		return label;
	}

	@Override
	public void startMatching(final MatchContext ctx, final int start, final MatchResults targetResults) {
		final ResultsInfo info= new ResultsInfo();
		
		MatchResults.Listener listener= new MatchResults.Listener() {
			@Override
			public void onMatchFound(MatchResults results, Match node) {
				// only add the first match
				if (results.getMatches().isEmpty())
					targetResults.addMatch(node);
			}

			@Override
			public void onMatchError(MatchResults results, MatchError matchError) {
				if (info.results.size() <= 1) {
					// if only a single result set is wrapped then we can set the 
					// error immediately
					targetResults.setError(matchError); 
				}
				else
					info.errors.add(matchError);
			}
		};
		
		for (int i= 0; i < matchers.size(); i++)  {
			MatchResults m= ctx.doMatch(matchers.get(i), start);
			m.addListener(listener);
		}
	}

}