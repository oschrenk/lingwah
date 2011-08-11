package com.googlecode.lingwah.parser;

import java.util.List;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;



public class MutableParser extends Parser
{
	private Parser _matcher;
	
	public MutableParser(Parser parser) {
		_matcher= parser;
	}

	public MutableParser define(Parser definition)
	{
		this._matcher = definition;
		return this;
	}

	public Parser getDefinition() {
		return _matcher;
	}
	
	
	@Override
	public String getDefaultLabel() {
		return "define("+_matcher.getLabel()+")";
	}
	

	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults parseResults) {
		_matcher.startMatching(ctx, start, parseResults);
	}

	@Override
	public void completeMatching(ParseContext ctx, int start, ParseResults parseResults) {
		_matcher.completeMatching(ctx, start, parseResults);
	}

	@Override
	public List<Parser> getDependencies() {
		return _matcher.getDependencies();
	}
}
