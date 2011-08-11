/**
 * 
 */
package com.googlecode.lingwah.parser;

import com.googlecode.lingwah.Parser;



/**
 * A sequence parser that accepts whitespace between all the elements
 * @author ted stocwkell
 *
 */
public final class SpacedSequenceParser extends SequenceParser
{
	public SpacedSequenceParser(Parser whitespaceMatcher, Parser[] matchers)
	{
		super(getAugmentedMatcherArray(whitespaceMatcher, matchers));
	}


	private static Parser[] getAugmentedMatcherArray( Parser whitespaceMatcher, Parser[] matchers) {
		
		if (matchers.length <= 0)
			return new Parser[0];
		if (matchers.length == 1)
			return matchers;
		
		Parser[] m= new Parser[matchers.length*2 -1];
		int j= 0;
		for (int i= 0; i < matchers.length; i++) {
			m[j++]= matchers[i];
			if (i < matchers.length-1)
				m[j++]= whitespaceMatcher;
		}
		
		return m;
	}
}