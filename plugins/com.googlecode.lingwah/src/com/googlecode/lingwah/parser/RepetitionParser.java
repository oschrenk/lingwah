/**
 * 
 */
package com.googlecode.lingwah.parser;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.lingwah.Match;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseError;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.Parsers;

/**
 * Returns a result for each repetition of the given parser. 
 * @author Ted Stockwell
 *
 */
public final class RepetitionParser extends Parser
{
	private final Parser _matcher;
	private final boolean _isOptional;

	/**
	 * @param _isOptional true if repeat zero or more, else repeat one of more
	 */
	public RepetitionParser(Parser parser, boolean isOptional)
	{
		_matcher= parser;
		_isOptional= isOptional;
	}
	
	@Override
	public String getDefaultLabel() {
		return "repeat("+_matcher.getLabel()+")";
	}

	@Override
	public void startMatching(final ParseContext ctx, int start, final ParseResults targetResults) {
		
		class RepetitionListener implements ParseResults.Listener {
			private Match _previousMatch;
			RepetitionListener(Match previousMatch) {
				_previousMatch= previousMatch;
			}
			@Override
			public void onMatchFound(ParseResults results, final Match nextNode) {
				ArrayList<Match> children= new ArrayList<Match>();
				if (_previousMatch != null)
					children.addAll(_previousMatch.getChildren());
				children.add(nextNode);
				Match node= Match.create(ctx, targetResults.getMatcher(), children);
				targetResults.addMatch(node);
				ctx.doMatch(_matcher, nextNode.getEnd()).addListener(new RepetitionListener(node));
			}

			@Override
			public void onMatchError(ParseResults results, ParseError parseError) {
				if (_previousMatch == null) {
					targetResults.setError(parseError);
				}
			}
		};
		
		// add a match of zero length
		if (_isOptional)
			targetResults.addMatch(Match.create(ctx, this, start, start));
		
		ctx.doMatch(_matcher, start).addListener(new RepetitionListener(null));
	}
	
	@Override
	public List<Parser> getDependencies() {
		return Arrays.asList(new Parser[] { _matcher });
	}
	
	public Parser separatedBy(Parser separator) {
		Parser parser= Parsers.seq(_matcher, Parsers.opt(Parsers.rep(Parsers.seq(separator, _matcher))));
		if (_isOptional)
			parser= Parsers.opt(parser);
		return parser;
	}
	public final Parser sep(Parser separator) {
		return separatedBy(separator);
	}

	public boolean isOptional() {
		return _isOptional;
	}
}