package com.googlecode.lingwah.matcher;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchError;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.Matcher;
import com.googlecode.lingwah.node.Match;

public class OptionalMatcher extends Matcher {
	
	private Matcher _matcher;

	public OptionalMatcher(Matcher matcher) {
		_matcher= matcher;
	}
	
	@Override
	public String getDefaultLabel() {
		return "optional("+_matcher.getLabel()+")";
	}

	@Override
	public List<Matcher> getDependencies() {
		return Arrays.asList(new Matcher[] { _matcher });
	}

	@Override
	public void startMatching(MatchContext ctx, int start, final MatchResults targetResults) {
		
		// add a match of zero length
		targetResults.addMatch(Match.create(ctx, this, start, start));
		
		// add any other matches that are found
		ctx.doMatch(_matcher, start).addListener(new MatchResults.Listener() {
			@Override
			public void onMatchFound(MatchResults results, Match node) {
				targetResults.addMatch(node);
			}
			
			@Override
			public void onMatchError(MatchResults results, MatchError matchError) {
				// ignore
			}
		});
	}

	@Override
	public void completeMatching(MatchContext ctx, int start, MatchResults matchResults) {
		// nothing to do
		
	}
}