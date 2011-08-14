package com.googlecode.lingwah;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.googlecode.lingwah.parser.ChoiceParser;


public abstract class Parser
{
	/**
	 * Do not call this method directly, use the ParseContext.doMatch method
	 * to perform parsing.
	 * This method is used by ParseContext.   
	 */
	abstract public void startMatching(ParseContext ctx, int start, ParseResults parseResults);
	/**
	 * Do not call this method directly, use the ParseContext.doMatch method
	 * to perform parsing.
	 * This method is used by ParseContext.   
	 */
	abstract public void completeMatching(ParseContext ctx, int start, ParseResults parseResults);
	
	/**
	 * Returns a list of other Matchers that are used by this Parser.
	 * This list of dependencies is used to check for recursive parser dependencies.
	 * 
	 * The ability to detect recursion plays a part in the parser combinator 
	 * library by enabling the construction of Parser types that are not 
	 * safe when used with recursive parsers but other commonly used 
	 * anyway - with non-recursive Matchers. 
	 * For instance, ExcludingParser cannot safely accept recursive Matchers 
	 * as filters but is nevertheless a very useful Parser.
	 * So, ExcludingParser checks to make sure that no recursive Matchers are used as 
	 * filters.      
	 */
	abstract public List<Parser> getDependencies();
	
	private String _label;
	private Boolean _isRecursive;
	
	public Parser() {
	}
	
	final public String getLabel() {
		if (_label == null)
			return getDefaultLabel();
		return _label;
	}
	
	public String getDefaultLabel() {
		return getClass().getSimpleName()+"("+hashCode()+")";
	}

	public Parser optionFor(ChoiceParser choiceParser) {
		choiceParser.addChoice(this);
		return this;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
	
	public Parser setLabel(String label) {
		_label= label;
		return this;
	}
	
	public boolean isRecursive() {
		if (_isRecursive == null) {
			boolean isRecursive= false;
			HashSet<Parser> done= new HashSet<Parser>();
			ArrayList<Parser> todo= new ArrayList<Parser>();
			todo.addAll(getDependencies());
			while (!todo.isEmpty()) {
				Parser parser= todo.remove(0);
				if (!done.contains(parser)) {
					done.add(parser);
					if (parser == this) {
						isRecursive= true;
						break;
					}
					todo.addAll(parser.getDependencies());
				}
			}
			_isRecursive= isRecursive;
		}
		return _isRecursive;
	}
	
	public Parser excluding(Parser...filters) {
		return Parsers.excluding(this, filters);
	}
	
}