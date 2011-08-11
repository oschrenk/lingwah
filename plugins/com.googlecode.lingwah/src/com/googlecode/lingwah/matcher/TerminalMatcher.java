package com.googlecode.lingwah.matcher;

import java.util.Collections;
import java.util.List;

import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.Matcher;

/**
 * Base class for terminal matchers.
 * @author Ted Stockwell
 */
abstract public class TerminalMatcher extends Matcher {

	@Override
	public List<Matcher> getDependencies() {
		return Collections.<Matcher>emptyList();
	}
	
	@Override
	public boolean isRecursive() {
		return false;
	}
	
	@Override
	public void completeMatching(MatchContext ctx, int start, MatchResults matchResults) {
		// nothing to do
		
	}
}
