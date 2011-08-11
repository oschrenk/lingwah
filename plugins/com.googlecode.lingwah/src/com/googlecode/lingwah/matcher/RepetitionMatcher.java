/**
 * 
 */
package com.googlecode.lingwah.matcher;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchError;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.Matcher;
import com.googlecode.lingwah.node.Match;

/**
 * Returns a result for each repetition of the given matcher. 
 * @author Ted Stockwell
 *
 */
public final class RepetitionMatcher extends Matcher
{
	private final Matcher _matcher;

	public RepetitionMatcher(Matcher matcher)
	{
		_matcher= matcher;
	}
	
	@Override
	public String getDefaultLabel() {
		return "repeat("+_matcher.getLabel()+")";
	}

	@Override
	public void startMatching(final MatchContext ctx, int start, final MatchResults targetResults) {
		
		class RepetitionListener implements MatchResults.Listener {
			private Match _previousMatch;
			RepetitionListener(Match previousMatch) {
				_previousMatch= previousMatch;
			}
			@Override
			public void onMatchFound(MatchResults results, final Match nextNode) {
				ArrayList<Match> children= new ArrayList<Match>();
				if (_previousMatch != null)
					children.addAll(_previousMatch.getChildren());
				children.add(nextNode);
				Match node= Match.create(ctx, targetResults.getMatcher(), children);
				targetResults.addMatch(node);
				ctx.doMatch(_matcher, nextNode.getEnd()).addListener(new RepetitionListener(node));
			}

			@Override
			public void onMatchError(MatchResults results, MatchError matchError) {
				if (_previousMatch == null) {
					targetResults.setError(matchError);
				}
			}
		};
		
		ctx.doMatch(_matcher, start).addListener(new RepetitionListener(null));
	}

	@Override
	public void completeMatching(MatchContext ctx, int start, MatchResults matchResults) {
		// nothing to do
		
	}
	
	@Override
	public List<Matcher> getDependencies() {
		return Arrays.asList(new Matcher[] { _matcher });
	}
}