package com.googlecode.lingwah;


public interface Document {

	/**
	 * Returns this document's text for the specified range.
	 */
	public String substring(int start, int end);

	/**
	 * Returns this document's text starting at the given position to the end of the document.
	 */
	public String substring(int start);

	public int length();

	public char charAt(int i);

	public int indexOf(char startChar, int i);

	public boolean startsWith(String _beginMarker, int start);

	public int indexOf(String _endMarker, int i);

}
