package com.googlecode.lingwah.exception;

import com.googlecode.lingwah.Matcher;

public class RecursiveMatchersNotSupported extends GrammarParsingException {
	private static final long serialVersionUID = 1L;

	public RecursiveMatchersNotSupported(Matcher matcher) {
		super(matcher.getLabel());
	}

}
