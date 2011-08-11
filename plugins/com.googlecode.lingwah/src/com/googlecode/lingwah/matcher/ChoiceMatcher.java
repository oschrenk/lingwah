/**
 * 
 */
package com.googlecode.lingwah.matcher;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchError;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.Matcher;
import com.googlecode.lingwah.node.Match;

public class ChoiceMatcher extends Matcher
{
	protected final ArrayList<Matcher> matchers= new ArrayList<Matcher>();
	
	public ChoiceMatcher(Matcher[] matchers)
	{
		for (Matcher matcher:matchers)
			this.matchers.add(matcher);
	}
	public ChoiceMatcher(Collection<Matcher> matchers)
	{
		for (Matcher matcher:matchers)
			this.matchers.add(matcher);
	}
	public ChoiceMatcher()
	{
	}
	
	@Override
	public String getDefaultLabel() {
		String label= "choice(";
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
	
	protected static class ResultsInfo {
		final ArrayList<MatchResults> results= new ArrayList<MatchResults>();
		final ArrayList<MatchError> errors= new ArrayList<MatchError>();
	}

	
	@Override
	public void startMatching(final MatchContext ctx, final int start, final MatchResults targetResults) {
		
		if (matchers.size() <= 0) { 
			targetResults.setError("This choice contains no options");
			return;
		}
		
		final ResultsInfo info= new ResultsInfo();
		targetResults.putMatcherInfo(this, info);
		
		MatchResults.Listener listener= new MatchResults.Listener() {
			@Override
			public void onMatchFound(MatchResults results, Match node) {
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
			info.results.add(m);
		}
	}
	
	@Override
	public void completeMatching(MatchContext ctx, int start, MatchResults matchResults) {
		final ResultsInfo info= matchResults.getMatcherInfo(this);
		/*
		 * If no results found then return the latest occurring error
		 * among all the choices. 
		 */
		if (matchResults.getMatches().isEmpty()) {
			MatchError error= null;
			for (MatchError e:info.errors) {
				if (error == null || error.position < e.position)
					error= e;
			}
			if (error != null)
				matchResults.setError(error);
		}
	}
	

	public void addChoice(Matcher matcher) {
		this.matchers.add(matcher);
	}

	public void addChoices(Matcher... matchers) {
		for (Matcher matcher:matchers)
			this.matchers.add(matcher);
	}
	
	@Override
	public List<Matcher> getDependencies() {
		return Collections.unmodifiableList(matchers);
	}
}