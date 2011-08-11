package com.googlecode.lingwah;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.googlecode.lingwah.node.Match;

/**
 * Encapsulates (and potentially manipulates) the results of applying a matcher
 * to a particular position of an input stream.
 * The MatchContext class caches instances of this class so that no matcher is 
 * ever applied more than once to the same input position.
 * 
 * @See MatchContext
 * 
 * @author ted stockwell
 */
public class MatchResults {
	

	/**
	 * Listener interface for receiving notifications.
	 */
	static public interface Listener {
		
		public void onMatchFound(MatchResults results, Match node);

		public void onMatchError(MatchResults matchResults, MatchError error);
		
	}
	
	// forwards results to the given destination results.
	static public class DefaultListener implements Listener {
		
		final public MatchResults destinationResults;
		
		public DefaultListener(MatchResults results) {
			destinationResults= results;
		}
		
		public void onMatchFound(MatchResults results, Match node) {
			destinationResults.addMatch(node);
		}

		public void onMatchError(MatchResults matchResults, MatchError error) {
			destinationResults.setError(error);
		}
	}
	

	private final MatchContext _ctx;
	private final Matcher _matcher;
	private final int _position; 
	private List<Match> _matches;
	private MatchError _error;
	private HashSet<Listener> _listeners= new HashSet<Listener>();
	private HashMap<Matcher, Object> _properties= new HashMap<Matcher, Object>();

	public MatchResults(MatchContext ctx, Matcher matcher, int position) {
		_ctx= ctx;
		_matcher= matcher;
		_position= position;
	}
	
	
	
	public MatchResults setError(String msg) {
		setError(new MatchError(_matcher, msg, _position));
		return this;
	}
	public MatchResults setError(String msg, int position) {
		setError(new MatchError(_matcher, msg, position));
		return this;
	}
	
	public MatchResults addMatch(int endPosition) {
		addMatch(Match.create(_ctx, _matcher, _position, endPosition));
		return this;
	}
	
	
	public boolean success() {
		return _error == null && _matches != null && !_matches.isEmpty();
	}
	
	public List<Match> getMatches() {
		if (_matches == null)
			return Collections.<Match>emptyList();
		return Collections.unmodifiableList(_matches);
	}

	public int longestLength() {
		if (_matches == null)
			return 0;

		int l, m = 0;
		for (Match node : _matches) {
			if (m < (l = node.length()))
				m = l;
		}
		return m;
	}

	public String toString() {
		if (!success())
			return "Failed at position " + _error.position + ":"
					+ _error.errorMsg;
		String s = "Success, matched " + _matches.get(0).getText();
		for (int i = 1; i < _matches.size(); i++)
			s += "\nand " + _matches.get(i).getText();
		return s;
	}



	public Matcher getMatcher() {
		return _matcher;
	}



	public int getPosition() {
		return _position;
	}

	synchronized public void addListener(Listener listener) {
		if (listener != null) {
			if (!_listeners.contains(listener)) {
				_listeners.add(listener);
				if (_matches != null) {
					for (Match node : new ArrayList<Match>(_matches)) {
						listener.onMatchFound(this, node);
					}
				}
			}
		}
	}
	synchronized public void addMatch(Match match) {
		if (match.getMatcher() != _matcher)
			match= Match.create(_ctx, _matcher, Arrays.asList(new Match[] { match }));
		if (_matches == null) 
			_matches= new ArrayList<Match>();
		for (Match m:_matches)
			if (m.length() == match.length())
				return;
		if (!_matches.contains(match)) {
			_matches.add(match);
			for (Listener listener:_listeners) {
				listener.onMatchFound(this, match);
			}
		}
	}
	public void setError(MatchError error) {
		// just save the error.
		// if no matches are found then the error will be send to listeners
		if (_error == null || error.position < _error.position)
			_error= error;
		for (Listener listener:_listeners) {
			listener.onMatchError(this, _error);
		}
	}
	
	public MatchError getError() {
		return _error;
	}
	
	public MatchContext getContext() { return _ctx; }
	
	@SuppressWarnings("unchecked")
	public <T> T getMatcherInfo(Matcher matcher) {
		return (T) _properties.get(matcher);
	}
	public <T> T getMatcherInfo() {
		return getMatcherInfo(getMatcher());
	}
	public <T> void putMatcherInfo(Matcher matcher, T info) {
		_properties.put(matcher, info);
	}
	public <T> void putMatcherInfo(T info) {
		_properties.put(getMatcher(), info);
	}



	public String getErrorMessage() {
		if (_error == null)
			return null;
		return _error.errorMsg;
	}

	public Match getLongestMatch() {
		if (_matches == null)
			return null;

		Match longestMatch= null;
		for (Match node : _matches) {
			if (longestMatch == null || longestMatch.length() < node.length())
				longestMatch= node;
		}
		return longestMatch;
	}
}
