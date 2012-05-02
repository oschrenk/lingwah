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
	private final boolean _insensitive;

	public StringParser(String target, boolean insensitive)
	{
		_target= target;
		_insensitive= insensitive;
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
		while (i < l && (start+i) < e) {
			char c= _target.charAt(i);
			if (input.charAt(start + i) == c) {
				// fall through
			}
			else if (_insensitive && (c == Character.toLowerCase(c) || c == Character.toUpperCase(c))) {
				// fall through
			}
			else
				break;
			
			i++;
		}
		if (i == l) { 
			parseResults.addMatch(start + l);
		}
		else
			parseResults.setError("Expected '"+_target+"'", start + i);
	}

}