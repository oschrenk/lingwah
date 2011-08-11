package com.googlecode.lingwah.matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchError;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.Matcher;
import com.googlecode.lingwah.Parsers;
import com.googlecode.lingwah.node.Match;

public class SequenceMatcher extends Matcher {
	private static class ResultsInfo {
		MatchError error;
		public void collectErrorInfo(MatchError e) {
			if (error == null || error.position < e.position)
				error= e;
		}
	}
	
	private final Matcher[] matchers;
	private final Matcher _tailMatcher;

	public SequenceMatcher(Matcher[] matchers) {
		this.matchers = matchers;
		_tailMatcher= matchers.length <= 1 ? null : Parsers.seq(Parsers.tail(matchers));
	}
	
	@Override
	public String getDefaultLabel() {
		String label= "sequence(";
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
	public void startMatching(final MatchContext ctx, int start, final MatchResults targetResults) {
		if (matchers.length <= 0) {
			targetResults.setError("This sequence contains no elements");
			return;
		}
		
		final ResultsInfo info= new ResultsInfo();
		targetResults.putMatcherInfo(info);
		
		MatchResults headResults= ctx.doMatch(matchers[0], start);
		if (matchers.length <= 1) {
			headResults.addListener(new MatchResults.Listener() {
				@Override
				public void onMatchFound(MatchResults results, Match node) {
					ArrayList<Match> children= new ArrayList<Match>();
					children.add(node);
					targetResults.addMatch(Match.create(ctx, targetResults.getMatcher(), children));
				}
				
				@Override
				public void onMatchError(MatchResults matchResults, MatchError error) {
					ResultsInfo info= targetResults.getMatcherInfo();
					info.collectErrorInfo(error);
				}
			});
			return;
		}

		headResults.addListener(new MatchResults.Listener() {
			@Override
			public void onMatchFound(MatchResults results, final Match headNode) {
				MatchResults tailResults= ctx.doMatch(_tailMatcher, headNode.getEnd());
				tailResults.addListener(new MatchResults.Listener() {
					@Override
					public void onMatchFound(MatchResults results, Match tailNode) {
						ArrayList<Match> children= new ArrayList<Match>();
						children.add(headNode);
						children.addAll(tailNode.getChildren());
						assert !tailNode.getChildren().isEmpty();
						targetResults.addMatch(Match.create(ctx, targetResults.getMatcher(), children));
					}
					
					@Override
					public void onMatchError(MatchResults results, MatchError matchError) {
						ResultsInfo info= targetResults.getMatcherInfo();
						info.collectErrorInfo(matchError);
					}
				});
			}

			@Override
			public void onMatchError(MatchResults results, MatchError matchError) {
				ResultsInfo info= targetResults.getMatcherInfo();
				info.collectErrorInfo(matchError);
			}
		});
	}
	
	@Override
	public List<Matcher> getDependencies() {
		return Arrays.asList(matchers);
	}

	@Override
	public void completeMatching(MatchContext ctx, int start, MatchResults matchResults) {
		// set error if no matches found
		if (matchResults.getMatches().isEmpty()) {
			ResultsInfo info= matchResults.getMatcherInfo();
			if (info.error != null)
				matchResults.setError(info.error);
		}
	}
	
}