package com.googlecode.lingwah.node;

import java.math.BigDecimal;

abstract public class AbstractVisitor implements MatchVistor {
	public boolean visit(Match node) {
		
	}
	
	/**
	 * Invoked after invoking the visit method and visiting all children nodes   
	 */
	public void leave(Match node) {
		
	}

	public void putResult(BigDecimal result) {
		// TODO Auto-generated method stub
		
	}

	public BigDecimal getResult(Match childByType) {
		// TODO Auto-generated method stub
		return null;
	}

}
