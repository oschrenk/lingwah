package com.googlecode.lingwah.exception;


public class UndefinedSymbolException extends GrammarParsingException
{
	private static final long	serialVersionUID	= 1L;

	public UndefinedSymbolException(String symbol)
	{
		super("Undefined symbol:"+symbol);
	}
}
