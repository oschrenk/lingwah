package com.googlecode.lingwah;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A ParseContext is used to invoke parse text by invoking the doMatch method 
 * and passing a Parser.
 * 
 * ParseContext manages the parsing process.
 * ParseContext implements strategies for memoizing match results 
 * and avoiding recursion.
 * 
 * Specifically, ParseContext avoids left recursion using the memoization 
 * techniques described in the paper "Memoization in Top-Down Parsing" by
 * Mark Johnson 
 *   
 * @author ted stockwell
 *
 */
public class ParseContext {
	private static String tabstring= "\t";
	private final String tabs() {
		if (tabstring.length() < _tabs) {
			tabstring= "";
			for (int i= _tabs*2; 0 < i--;)
				tabstring+= '\t';
		}
		return tabstring.substring(0, _tabs);
	}
	
	
	
	
	private Document _input;
	private HashMap<Parser, Map<Integer, ParseResults>> _cache= 
		new HashMap<Parser, Map<Integer,ParseResults>>();
	
	private boolean _trace= false;
	private int _tabs= 0;
	private HashMap<Parser, Boolean> _traceFlags= new HashMap<Parser, Boolean>();
	private boolean _inProgress;
	private ArrayList<ParseResults> _callStack= new ArrayList<ParseResults>();
	
	
	/**
	 * A convenience method for performing a single match.
	 */
	public static ParseResults parse(Parser parser, Document input) {
		return new ParseContext(input).getParseResults(parser, 0);
	}
	/**
	 * A convenience method for performing a single match.
	 */
	public static ParseResults parse(Parser parser, String input) {
		return new ParseContext(input).getParseResults(parser, 0);
	}
	
	public ParseContext(Document input) {
		_input= input;
	}
	public ParseContext(String input) {
		_input= new StringDocument(input);
	}
	
	
	/**
	 * This method is used to perform matches.
	 */
	public ParseResults doMatch(Parser parser, int start) {
		
		// if the given parser has already been invoked at the given position 
		// then just return the current results.  
		ParseResults entry= getCachedResults(parser, start);
		if (entry != null) 
			return entry;

		// create new results for the parser invocation 
		entry = parser.createResults(this, start);
		cacheMatchResults(entry);
		_callStack.add(entry);
		
		if (_inProgress) 
			return entry;
		_inProgress= true;

		while (!_callStack.isEmpty()) {
			entry= _callStack.remove(0);
			parser= entry.getMatcher();
			start= entry.getPosition();

			// setup trace 
			boolean otrace= _trace;
			Boolean traceFlag= _traceFlags.get(parser);
			if (traceFlag != null) 
				_trace= traceFlag.booleanValue();
			boolean trace= _trace;
			if (trace) _tabs++;
			if (trace) System.out.println(tabs()+"doMatch("+parser.getLabel()+","+start+")");		
			try {
				parser.startMatching(this, start, entry);
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
	

	public Document getDocument() {
		return _input;
	}

	public ParseResults getCachedResults(Parser parser, int start) {
		ParseResults entry= null;
		Map<Integer,ParseResults> entries= _cache.get(parser);
		if (entries != null)
			entry= entries.get(start);
		return entry;
	}
	
	protected void cacheMatchResults(ParseResults  parseResults) {
		Parser parser= parseResults.getMatcher();
		Map<Integer,ParseResults> results= _cache.get(parser);
		if (results == null) {
			results= new HashMap<Integer, ParseResults>();
			_cache.put(parser, results);
		}
		int start= parseResults.getPosition();
		ParseResults entry= results.get(start);
		assert entry == null : "Internal Error: Results have already been cached";
		results.put(start, parseResults);
	}
	
	public void trace(Parser parser, boolean trace) {
		_traceFlags.put(parser, trace ? Boolean.TRUE : Boolean.FALSE);
	}


		
	/**
	 * A convenience method for performing a single match.
	 * This method should only be used for Matchers that are terminals, since they 
	 * are guaranteed to complete during the call to doMatch.
	 * Maybe non-terminals also complete during a call to doMatch but I do not 
	 * know that for sure.  
	 */
	public ParseResults getParseResults(Parser parser, int start) {
		doMatch(parser, start);
		ParseResults parseResults= getCachedResults(parser, start);
		assert parseResults != null;
		return parseResults;
	}
	
}
