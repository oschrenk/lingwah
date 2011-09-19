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

	public char charAt(int index);

	public int indexOf(int ch, int fromIndex);

	public boolean startsWith(String prefix, int offset);

	public int indexOf(String str, int fromIndex);

}
