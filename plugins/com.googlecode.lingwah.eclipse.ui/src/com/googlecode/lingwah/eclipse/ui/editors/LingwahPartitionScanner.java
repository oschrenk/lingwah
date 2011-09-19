package com.googlecode.lingwah.eclipse.ui.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.rules.IToken;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;

public class LingwahPartitionScanner 
implements IPartitionTokenScanner 
{
	private EclipseGrammarAdapter _grammar;
	private ParseContext _parseContext;
	private EclipseDocumentAdapter _document;
	
	// current range
	private int _rangeOffset;
	private int _rangeLength;
	private String _rangeContentType;
	private int _rangePartitionOffset;
	private ParseResults _lastToken;

	public LingwahPartitionScanner(EclipseDocumentAdapter document) {
		_grammar= _document.getGrammar();
		_parseContext= _document.getParseContext();
	}

	@Override
	public void setRange(IDocument document, int offset, int length) {
		assert document == _document.getEclipseDocument();
		_rangeOffset= offset;
		_rangeLength= length;
		_rangeContentType= null;
		_lastToken= null;
	}

	@Override
	public IToken nextToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTokenOffset() {
		if (_lastToken == null)
			return _rangeOffset;
		return _lastToken.getPosition();
	}

	@Override
	public int getTokenLength() {
		if (_lastToken == null)
			return 0;
		return _lastToken.longestLength();
	}

	@Override
	public void setPartialRange(IDocument document, int offset, int length, String contentType, int partitionOffset) {
		assert document == _document.getEclipseDocument();
		_rangeOffset= offset;
		_rangeLength= length;
		_rangeContentType= contentType;
		_rangePartitionOffset= partitionOffset;
	}
}
