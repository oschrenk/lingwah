package com.googlecode.lingwah.exception;

public abstract class GrammarParsingException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public GrammarParsingException(String message)
	{
		super(message);
	}
}
