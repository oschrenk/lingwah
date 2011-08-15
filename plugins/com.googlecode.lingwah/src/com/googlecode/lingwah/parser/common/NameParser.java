package com.googlecode.lingwah.parser.common;

import com.googlecode.lingwah.Document;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.parser.TerminalParser;

/**
 * Matches names. Originally developed to match names as defined by the RDF
 * Notation3 language. Override the isNameStart and isNameChar methods to
 * customize for other languages.
 * 
 * @author Ted Stockwell
 */
public class NameParser extends TerminalParser {

	@Override
	public void startMatching(ParseContext ctx, int start, ParseResults results)
	{
		final Document input = ctx.getDocument();
		
		if (input.length() <= start) { 
			results.setError("A name must have at least one letter");
			return;
		}

		if (!isNameStartChar(input.charAt(start))) {
			results.setError("Not a valid name start charaacter '" + input.charAt(start) + "'");
			return;
		}
		
		int i = start + 1;
		for (; i < input.length(); i++) {
			if (!isNameChar(input.charAt(i)))
				break;
		}
		results.addMatch(i);
	}

	@Override
	public String getDefaultLabel() {
		return "name";
	}

	public boolean isNameStartChar(char c) {

		if ('A' <= c && c <= 'Z')
			return true;

		if ('a' <= c && c <= 'z')
			return true;

		if (c == '_')
			return true;

		if (0x00C0 <= c && c <= 0x00D6)
			return true;

		if (0x00D8 <= c && c <= 0x00F6)
			return true;

		if (0x00F8 <= c && c <= 0x02FF)
			return true;

		if (0x0370 <= c && c <= 0x037D)
			return true;

		if (0x037F <= c && c <= 0x1FFF)
			return true;

		if (0x200C <= c && c <= 0x200D)
			return true;

		if (0x2070 <= c && c <= 0x218F)
			return true;

		if (0x2C00 <= c && c <= 0x2FEF)
			return true;

		if (0x3001 <= c && c <= 0xD7FF)
			return true;

		if (0xF900 <= c && c <= 0xFDCF)
			return true;

		if (0xFDF0 <= c && c <= 0xFFFD)
			return true;

		if (0x10000 <= c && c <= 0xEFFFF)
			return true;

		return false;

	}

	public boolean isNameChar(char c) {
		if (isNameStartChar(c))
			return true;

		if (0xFDF0 <= c && c <= 0xFFFD)
			return true;

		if ('0' <= c && c <= '9')
			return true;

		if ('-' == c)
			return true;

		if (0x00B7 == c)
			return true;

		if (0x0300 <= c && c <= 0x036F)
			return true;

		if (0x203F <= c && c <= 0x2040)
			return true;

		return false;
	}
}
