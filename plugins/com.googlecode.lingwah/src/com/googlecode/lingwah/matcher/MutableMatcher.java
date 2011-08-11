package com.googlecode.lingwah.matcher;

import java.util.List;

import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.Matcher;



public class MutableMatcher extends Matcher
{
	private Matcher _matcher;
	
	public MutableMatcher(Matcher matcher) {
		_matcher= matcher;
	}

	public MutableMatcher define(Matcher definition)
	{
		this._matcher = definition;
		return this;
	}

	public Matcher getDefinition() {
		return _matcher;
	}
	
	
	@Override
	public String getDefaultLabel() {
		return "define("+_matcher.getLabel()+")";
	}
	

	@Override
	public void startMatching(MatchContext ctx, int start, MatchResults matchResults) {
		_matcher.startMatching(ctx, start, matchResults);
	}

	@Override
	public void completeMatching(MatchContext ctx, int start, MatchResults matchResults) {
		_matcher.completeMatching(ctx, start, matchResults);
	}

	@Override
	public List<Matcher> getDependencies() {
		return _matcher.getDependencies();
	}
}
