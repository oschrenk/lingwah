package com.googlecode.lingwah.eclipse.ui.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.*;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.ParseContext;

public class LingwahPartitionScanner 
implements IPartitionTokenScanner 
{
	private EclipseGrammarAdapter _grammar;
	private ParseContext _parseContext;

	public LingwahPartitionScanner(ParseContext parseContext, EclipseGrammarAdapter grammar) {
		_grammar= grammar;
		_parseContext= parseContext;
	}

	@Override
	public void setRange(IDocument document, int offset, int length) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IToken nextToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTokenOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTokenLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPartialRange(IDocument document, int offset, int length,
			String contentType, int partitionOffset) {
		// TODO Auto-generated method stub
		
	}
}
