package com.googlecode.lingwah.eclipse.ui.editors;

import org.eclipse.jface.text.IDocument;

import com.googlecode.lingwah.Document;

public class EclipseDocumentAdapter implements Document {
	
	private IDocument _document;

	public EclipseDocumentAdapter(IDocument document) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String substring(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String substring(int start) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int length() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char charAt(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int indexOf(char startChar, int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean startsWith(String _beginMarker, int start) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int indexOf(String _endMarker, int i) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public IDocument getDocument() {
		return _document;
	}

}
