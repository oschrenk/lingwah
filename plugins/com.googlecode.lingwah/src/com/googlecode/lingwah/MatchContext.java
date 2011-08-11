package com.googlecode.lingwah;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A MatchContext is used to invoke parse text by invoking the doMatch method 
 * and passing a Matcher.
 * 
 * MatchContext manages the parsing process.
 * MatchContext implements strategies for memoizing match results 
 * and avoiding recursion.
 * 
 * Specificially, MatchContext avoids left recursion using the memoization 
 * techniques described in the paper "Memoization in Top-Down Parsing" by
 * Mark Johnson 
 *   
 * @author ted stockwell
 *
 */
public class MatchContext {
	private static final long serialVersionUID = 1L;
	private static String tabstring= "\t";
	private final String tabs() {
		if (tabstring.length() < _tabs) {
			tabstring= "";
			for (int i= _tabs*2; 0 < i--;)
				tabstring+= '\t';
		}
		return tabstring.substring(0, _tabs);
	}
	
	
	
	
	private String _input;
	private HashMap<Matcher, Map<Integer, MatchResults>> _cache= 
		new HashMap<Matcher, Map<Integer,MatchResults>>();
	
	private boolean _trace= false;
	private int _tabs= 0;
	private HashMap<Matcher, Boolean> _traceFlags= new HashMap<Matcher, Boolean>();
	private boolean _inProgress;
	private ArrayList<MatchResults> _callStack= new ArrayList<MatchResults>();
	
	
	/**
	 * A convenience method for performing a single match.
	 */
	public static MatchResults match(Matcher matcher, String input) {
		return new MatchContext(input).getMatchResults(matcher, 0);
	}
	
	public MatchContext(String input) {
		_input= input;
	}
	
	
	/**
	 * This method is used to perform matches.
	 */
	public MatchResults doMatch(Matcher matcher, int start) {
		MatchResults entry= getCachedResults(matcher, start);
		if (entry != null) 
			return entry;

		entry = new MatchResults(this, matcher, start);
		cacheMatchResults(entry);
		_callStack.add(entry);
		
		if (_inProgress) 
			return entry;
		_inProgress= true;

		while (!_callStack.isEmpty()) {
			entry= _callStack.remove(0);
			matcher= entry.getMatcher();
			start= entry.getPosition();

			// setup trace 
			boolean otrace= _trace;
			Boolean traceFlag= _traceFlags.get(matcher);
			if (traceFlag != null) 
				_trace= traceFlag.booleanValue();
			boolean trace= _trace;
			if (trace) _tabs++;
			if (trace) System.out.println(tabs()+"doMatch("+matcher.getLabel()+","+start+")");		
			try {
				matcher.startMatching(this, start, entry);
			} 
			finally {
				if (trace) {
					System.out.println(tabs()+"/doMatch");		
					_tabs--;
				}
				_trace= otrace;
			}
		}
		
		_inProgress=false;	
		
		return entry;
	}
	

	public String getInput() {
		return _input;
	}

	public MatchResults getCachedResults(Matcher matcher, int start) {
		MatchResults entry= null;
		Map<Integer,MatchResults> entries= _cache.get(matcher);
		if (entries != null)
			entry= entries.get(start);
		return entry;
	}
	
	protected void cacheMatchResults(MatchResults  matchResults) {
		Matcher matcher= matchResults.getMatcher();
		Map<Integer,MatchResults> results= _cache.get(matcher);
		if (results == null) {
			results= new HashMap<Integer, MatchResults>();
			_cache.put(matcher, results);
		}
		int start= matchResults.getPosition();
		MatchResults entry= results.get(start);
		assert entry == null : "Internal Error: Results have already been cached";
		results.put(start, matchResults);
	}
	
	public void trace(Matcher matcher, boolean trace) {
		_traceFlags.put(matcher, trace ? Boolean.TRUE : Boolean.FALSE);
	}


		
	/**
	 * A convenience method for performing a single match.
	 * This method should only be used for Matchers that are terminals, since they 
	 * are guaranteed to complete during the call to doMatch.
	 * Maybe non-terminals also complete during a call to doMatch but I do not 
	 * know that for sure.  
	 */
	public MatchResults getMatchResults(Matcher matcher, int start) {
		doMatch(matcher, start);
		return getCachedResults(matcher, start);
	}
	
}
