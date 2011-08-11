package com.googlecode.lingwah;

public class MatchError {
	/**
	 * A message that describes the parsing failure.
	 */
	public final String errorMsg;
	
	/**
	 * The position within the parsed document where matching failed.
	 */
	public final int position;
	
	/**
	 * The matcher that created this error
	 */
	public final Matcher matcher;
	
	public MatchError(Matcher matcher, String msg, int position) {
		this.matcher= matcher;
		this.errorMsg= msg;
		this.position= position;
	}
	
	@Override
	public String toString() {
		return "{matcher:"+matcher+"position:"+position+",message:"+errorMsg+"}";
	}

}
