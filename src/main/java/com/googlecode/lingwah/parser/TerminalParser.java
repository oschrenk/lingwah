package com.googlecode.lingwah.parser;

import java.util.Collections;
import java.util.List;

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
}
