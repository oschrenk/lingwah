/**
 * 
 */
package com.googlecode.lingwah.parser;


import com.googlecode.lingwah.Document;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;

public final class StringParser extends TerminalParser
{
	private final String _target;

	public StringParser(String target)
	{
		_target= target;
	}
	
	@Override
	public String getDefaultLabel() {
		return "string('"+_target+"')";
	}

	public String getTarget() {
		return _target;
	}

	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults parseResults) {
		Document input= ctx.getDocument();
		int i= 0;
		int l= _target.length();
		int e= input.length();
		while (i < l && (start+i) < e && input.charAt(start + i) == _target.charAt(i))
			i++;
		if (i == l) { 
			parseResults.addMatch(start + l);
		}
		else
			parseResults.setError("Expected '"+_target+"'", start + i);
	}

	@Override
	public void completeMatching(ParseContext ctx, int start, ParseResults parseResults) {
		// nothign to do
		
	}
}