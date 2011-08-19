package com.googlecode.lingwah;

/**
 * An interface to be implemented by all Match processors.
 * A match processor is very much like a Visitor, except it has two methods:
 *  * the process method, like the visit method in the Visitor pattern, is invoked when visiting a node in the AST tree, and
 *  * the complete method, which is invoked after invoking the visit method and visiting all children nodes.
 *  
 * The complete method is convenient for computing results which require 
 * children nodes to be processed before a result can be calculated.
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
