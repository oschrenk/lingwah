package com.googlecode.lingwah.exception;

import com.googlecode.lingwah.Parser;


public class RecursiveMatchersNotSupported extends GrammarParsingException {
	private static final long serialVersionUID = 1L;

	public RecursiveMatchersNotSupported(Parser parser) {
		super(parser.getLabel());
	}

}
