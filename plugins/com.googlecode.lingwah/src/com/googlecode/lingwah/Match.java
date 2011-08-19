package com.googlecode.lingwah;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.googlecode.lingwah.parser.ParserReference;
import com.googlecode.lingwah.util.MatchNavigation;
import com.googlecode.lingwah.util.MatchUtils;

public class Match {
	
	private List<Match> children;
	
	private ParseContext _ctx; 

	/**
	 * The parser that created this node.
	 */
	protected Parser _matcher;

	/**
	 * A String representation of the associated grammar element. This text is
	 * not necessarily the same as the contents of the parsed document.
	 */
	protected String text;

	/**
	 * The beginning position within the parsed document of the grammar element
	 * represented by this node
	 */
	protected int start = -2;

	/**
	 * The ending position, plus one, of the grammar element represented by this
	 * node in the original parsed document.
	 */
	protected int end = -2;
	
	private Integer _hashcode;

	public static Match create( ParseContext ctx, Parser parser, final int start, final int end) {
		Match match= new Match();
		match._ctx= ctx;
		match.start = start;
		match.end = end;
		match._matcher = parser;
		return match;
	}
	
	public static Match create( ParseContext ctx, Parser parser, List<Match> children) {
		Match match= new Match();
		match._ctx= ctx;
		match._matcher = parser;
		Match first= children.get(0);
		Match last= children.get(children.size()-1);
		match.start= first.getStart();
		match.end= last.getEnd();
		match.setChildren(children);
		return match;
	}
	
	private Match() {
		start= end= 0;
	}

	public int length() {
		return end - start;
	}

	public int getStart() {
		return start;
	}

	private void setChildren(List<Match> children) {
		setChildren(children.toArray(new Match[children.size()]));
	}

	private void setChildren(Match[] children) {
		
		
//		// detect recursion
//		// for debugging only
//		ArrayList<Match> done= new ArrayList<Match>();
//		ArrayList<Match> todo= new ArrayList<Match>();
//		todo.addAll(Arrays.asList(children));
//		while (!todo.isEmpty()) {
//			Match e= todo.remove(0);
//			if (!done.contains(e)) {
//				if (e.equals(this) && 0 < e.length())
//					throw new RuntimeException("element recursion detected");
//				done.add(e);
//				todo.addAll(e.getChildren());
//			}
//		}
		
//		if (start == end)
//			throw new RuntimeException("Zero length nodes may not have chilren");
		
		if (this.children != null)
			throw new RuntimeException("Children may only be set once");
		this.children = new ArrayList<Match>(children.length);
		Match prev= null;
		for (Match c : children) {
			if (prev == null || !c.equals(prev))
				this.children.add(c);
			prev= c;
		}
	}

	public List<Match> getChildren() {
		if (children == null)
			return Collections.emptyList();
		return Collections.unmodifiableList(this.children);
	}

	public String getText() {
		if (this.text == null) {
			text= _ctx.getDocument().substring(start, end);
		}
		return this.text;
	}
	
	@Override
	public String toString() {
		return MatchUtils.toXML(this);
	}

	public Parser getParser() {
		return _matcher;
	}

	public int getEnd() {
		return end;
	}
	
	/**
	 * returns true if this node contains the given node
	 */
	public boolean contains(Match node) {
		if (this == node)
			return true;
		if (children != null) {
			for (Match c:children) {
				if (c.contains(node))
					return true;
			}
		}
		return false;
	}
	
    public int hashCode() {
    	if (_hashcode == null) {
        	int hashCode = _ctx.hashCode();
        	hashCode= 31*hashCode + _matcher.hashCode();
        	hashCode= 31*hashCode + start;
        	hashCode= 31*hashCode + end;
        	if (start < end && children != null) {
        		for (Match child:children)
        	    	hashCode= 31*hashCode + child.hashCode();
        	}
        	_hashcode= hashCode;
    	}
    	return _hashcode;
     }
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Match))
			return false;
		Match e= (Match)obj;
		
		// check for null(empty) nodes
		if (e.end <= e.start) {
			return end <= start;
		}
		else if (end <= start)
			return false;
		
		if (e._ctx != _ctx)
			return false;
		if (e._matcher != _matcher)
			return false;
		if (e.start != start)
			return false;
		if (e.end != end)
			return false;
		if (e.children == null) {
			if (children != null)
				return false;
		}
		else {
			if (children == null)
				return false;
			if (e.children.size() != children.size()) 
				return false;
				for (int i= children.size(); 0 < i--;) {
					Match c1= e.children.get(i);
					Match c2= children.get(i);
					if (!c1.equals(c2))
						return false;
				}
		}

		return true;
	}

	public Match getChildByType(Parser parser) {
		return MatchNavigation.findChildByType(this, parser);
	}

	public List<Match> getChildrenByType(ParserReference parser) {
		return MatchNavigation.findChildrenByType(this, parser);
	}
	
	public Match getFirstChild() {
		if (children == null || children.isEmpty())
			return null;
		return children.get(0);
	}
	
	public Match getLastChild() {
		if (children == null || children.isEmpty())
			return null;
		return children.get(children.size()-1);
	}
	

	public void accept(MatchProcessor visitor) {
		boolean visitChildren= visitor.process(this);
		if (visitChildren) {
			for (Match node : getChildren())
				node.accept(visitor);
		}
		visitor.complete(this);
	}
	
	
}
