package com.googlecode.lingwah;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.googlecode.lingwah.matcher.ChoiceMatcher;


public abstract class Matcher
{
	/**
	 * Do not call this method directly, use the MatchContext.doMatch method
	 * to perform parsing.
	 * This method is used by MatchContext.   
	 */
	abstract public void startMatching(MatchContext ctx, int start, MatchResults matchResults);
	/**
	 * Do not call this method directly, use the MatchContext.doMatch method
	 * to perform parsing.
	 * This method is used by MatchContext.   
	 */
	abstract public void completeMatching(MatchContext ctx, int start, MatchResults matchResults);
	
	/**
	 * Returns a list of other Matchers that are used by this Matcher.
	 * This list of dependencies is used to check for recursive matcher dependencies.
	 * 
	 * The ability to detect recursion plays a part in the parser combinator 
	 * library by enabling the construction of Matcher types that are not 
	 * safe when used with recursive matchers but other commonly used 
	 * anyway - with non-recursive Matchers. 
	 * For instance, ExcludingMatcher cannot safely accept recursive Matchers 
	 * as filters but is nevertheless a very useful Matcher.
	 * So, ExcludingMatcher checks to make sure that no recursive Matchers are used as 
	 * filters.      
	 */
	abstract public List<Matcher> getDependencies();
	
	private String _label;
	private Boolean _isRecursive;
	
	public Matcher() {
	}
	
	final public String getLabel() {
		if (_label == null)
			return getDefaultLabel();
		return _label;
	}
	
	public String getDefaultLabel() {
		return getClass().getSimpleName()+"("+hashCode()+")";
	}

	public Matcher optionFor(ChoiceMatcher choiceMatcher) {
		choiceMatcher.addChoice(this);
		return this;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
	
	public Matcher setLabel(String label) {
		_label= label;
		return this;
	}
	
	public boolean isRecursive() {
		if (_isRecursive == null) {
			boolean isRecursive= false;
			HashSet<Matcher> done= new HashSet<Matcher>();
			ArrayList<Matcher> todo= new ArrayList<Matcher>();
			todo.addAll(getDependencies());
			while (!todo.isEmpty()) {
				Matcher matcher= todo.remove(0);
				if (!done.contains(matcher)) {
					done.add(matcher);
					if (matcher == this) {
						isRecursive= true;
						break;
					}
					todo.addAll(matcher.getDependencies());
				}
			}
			_isRecursive= isRecursive;
		}
		return _isRecursive;
	}
	
}