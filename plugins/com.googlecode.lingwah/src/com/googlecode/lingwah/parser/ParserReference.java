package com.googlecode.lingwah.parser;

import java.util.List;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;



public class ParserReference extends Parser
{
	private Parser _definition;
	
	public ParserReference() {
	}
	public ParserReference(Parser definition) {
		_definition= definition;
	}

	public ParserReference define(Parser definition)
	{
		this._definition = definition;
		return this;
	}

	public Parser getDefinition() {
		return _definition;
	}
	
	
	@Override
	public String getDefaultLabel() {
		return "define("+_definition.getLabel()+")";
	}
	

	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults parseResults) {
		_definition.startMatching(ctx, start, parseResults);
	}

	@Override
	public List<Parser> getDependencies() {
		return _definition.getDependencies();
	}
}
