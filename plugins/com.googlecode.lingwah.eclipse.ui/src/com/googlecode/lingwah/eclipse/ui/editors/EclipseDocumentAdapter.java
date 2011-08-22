package com.googlecode.lingwah.eclipse.ui.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

import com.googlecode.lingwah.Document;

public class EclipseDocumentAdapter implements Document {
	
	private IDocument _document;
	private FindReplaceDocumentAdapter _findReplaceAdapter; 
	

	public EclipseDocumentAdapter(IDocument document) {
		_document= document;
		_findReplaceAdapter= new FindReplaceDocumentAdapter(document);
	}

	@Override
	public String substring(int start, int end) {
		try {
			return _document.get(start, end - start);
		} catch (BadLocationException e) {
			throw new StringIndexOutOfBoundsException(e.getMessage());
		}
	}

	@Override
	public String substring(int start) {
		try {
			return _document.get(start, _document.getLength() - start);
		} catch (BadLocationException e) {
			throw new StringIndexOutOfBoundsException(e.getMessage());
		}
	}

	@Override
	public int length() {
		return _document.getLength();
	}

	@Override
	public char charAt(int i) {
		try {
			return _document.getChar(i);
		} catch (BadLocationException e) {
			throw new StringIndexOutOfBoundsException(e.getMessage());
		}
	}

	@Override
	public int indexOf(int ch, int fromIndex) {
		return indexOf(""+ch, fromIndex);
	}

	@Override
	public boolean startsWith(String prefix, int toffset) {
		return substring(toffset).equals(prefix);
	}

	@Override
	public int indexOf(String target, int fromIndex) {
		try {
			IRegion region= _findReplaceAdapter.find(fromIndex, target, true, true, false, false);
			if (region == null)
				return -1;
			return region.getOffset();
		} catch (BadLocationException e) {
			throw new StringIndexOutOfBoundsException(e.getMessage());
		}
	}
	
	public IDocument getEclipseDocument() {
		return _document;
	}

}
