package com.googlecode.lingwah.parser;

import java.util.Collections;
import java.util.List;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;

/**
 * Base class for terminal parsers.
 * @author Ted Stockwell
 */
abstract public class TerminalParser extends Parser {

	@Override
	public List<Parser> getDependencies() {
		return Collections.<Parser>emptyList();
	}
	
	@Override
	public boolean isRecursive() {
		return false;
	}
	
	@Override
	public void completeMatching(ParseContext ctx, int start, ParseResults parseResults) {
		// nothing to do
		
	}
}
