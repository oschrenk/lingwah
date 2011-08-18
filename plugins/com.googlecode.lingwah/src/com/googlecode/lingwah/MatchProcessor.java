package com.googlecode.lingwah;

/**
 * An interface to be implemented by maych processor.
 * The match processor is very much like a Visitor.
 * 
 * @author Ted Stockwell
 */
public interface MatchProcessor {
	/**
	 * Invoked when visiting a node in the tree
	 * @return true if children of this node should be visited
	 */
	public boolean process(Match node);
	
	/**
	 * Invoked after invoking the visit method and visiting all children nodes   
	 */
	public void complete(Match node);
}
