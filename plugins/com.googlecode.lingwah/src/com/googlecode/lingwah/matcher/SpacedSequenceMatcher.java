/**
 * 
 */
package com.googlecode.lingwah.matcher;

import com.googlecode.lingwah.Matcher;



/**
 * A sequence matcher that accepts whitespace between all the elements
 * @author ted stocwkell
 *
 */
public final class SpacedSequenceMatcher extends SequenceMatcher
{
	public SpacedSequenceMatcher(Matcher whitespaceMatcher, Matcher[] matchers)
	{
		super(getAugmentedMatcherArray(whitespaceMatcher, matchers));
	}


	private static Matcher[] getAugmentedMatcherArray( Matcher whitespaceMatcher, Matcher[] matchers) {
		
		if (matchers.length <= 0)
			return new Matcher[0];
		if (matchers.length == 1)
			return matchers;
		
		Matcher[] m= new Matcher[matchers.length*2 -1];
		int j= 0;
		for (int i= 0; i < matchers.length; i++) {
			m[j++]= matchers[i];
			if (i < matchers.length-1)
				m[j++]= whitespaceMatcher;
		}
		
		return m;
	}
}