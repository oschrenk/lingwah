package com.googlecode.lingwah.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseError;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.Parsers;
import com.googlecode.lingwah.node.Match;

public class SequenceParser extends Parser {
	private static class ResultsInfo {
		ParseError error;
		public void collectErrorInfo(ParseError e) {
			if (error == null || error.position < e.position)
				error= e;
		}
	}
	
	private final Parser[] parsers;
	private final Parser _tailMatcher;

	public SequenceParser(Parser[] matchers) {
		this.parsers = matchers;
		_tailMatcher= matchers.length <= 1 ? null : Parsers.seq(Parsers.tail(matchers));
	}
	
	@Override
	public String getDefaultLabel() {
		String label= "sequence(";
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
	public void startMatching(final ParseContext ctx, int start, final ParseResults targetResults) {
		if (parsers.length <= 0) {
			targetResults.setError("This sequence contains no elements");
			return;
		}
		
		final ResultsInfo info= new ResultsInfo();
		targetResults.putMatcherInfo(info);
		
		ParseResults headResults= ctx.doMatch(parsers[0], start);
		if (parsers.length <= 1) {
			headResults.addListener(new ParseResults.Listener() {
				@Override
				public void onMatchFound(ParseResults results, Match node) {
					ArrayList<Match> children= new ArrayList<Match>();
					children.add(node);
					targetResults.addMatch(Match.create(ctx, targetResults.getMatcher(), children));
				}
				
				@Override
				public void onMatchError(ParseResults parseResults, ParseError error) {
					ResultsInfo info= targetResults.getMatcherInfo();
					info.collectErrorInfo(error);
				}
			});
			return;
		}

		headResults.addListener(new ParseResults.Listener() {
			@Override
			public void onMatchFound(ParseResults results, final Match headNode) {
				ParseResults tailResults= ctx.doMatch(_tailMatcher, headNode.getEnd());
				tailResults.addListener(new ParseResults.Listener() {
					@Override
					public void onMatchFound(ParseResults results, Match tailNode) {
						ArrayList<Match> children= new ArrayList<Match>();
						children.add(headNode);
						children.addAll(tailNode.getChildren());
						assert !tailNode.getChildren().isEmpty();
						targetResults.addMatch(Match.create(ctx, targetResults.getMatcher(), children));
					}
					
					@Override
					public void onMatchError(ParseResults results, ParseError parseError) {
						ResultsInfo info= targetResults.getMatcherInfo();
						info.collectErrorInfo(parseError);
					}
				});
			}

			@Override
			public void onMatchError(ParseResults results, ParseError parseError) {
				ResultsInfo info= targetResults.getMatcherInfo();
				info.collectErrorInfo(parseError);
			}
		});
	}
	
	@Override
	public List<Parser> getDependencies() {
		return Arrays.asList(parsers);
	}

	@Override
	public void completeMatching(ParseContext ctx, int start, ParseResults parseResults) {
		// set error if no matches found
		if (parseResults.getMatches().isEmpty()) {
			ResultsInfo info= parseResults.getMatcherInfo();
			if (info.error != null)
				parseResults.setError(info.error);
		}
	}
	
}