package com.googlecode.lingwah.node;

public interface MatchVistor {
	/**
	 * Invoked when visiting a node in the tree
	 * @return true if children of this node should be visited
	 */
	public boolean visit(Match node);
	
	/**
	 * Invoked after invoking the visit method and visiting all children nodes   
	 */
	public void leave(Match node);
}
