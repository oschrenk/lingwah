package com.googlecode.lingwah;

public class ParseError {
	/**
	 * A message that describes the parsing failure.
	 */
	public final String errorMsg;
	
	/**
	 * The position within the parsed document where matching failed.
	 */
	public final int position;
	
	/**
	 * The parser that created this error
	 */
	public final Parser parser;
	
	public ParseError(Parser parser, String msg, int position) {
		this.parser= parser;
		this.errorMsg= msg;
		this.position= position;
	}
	
	@Override
	public String toString() {
		return "{parser:"+parser+"position:"+position+",message:"+errorMsg+"}";
	}

}
