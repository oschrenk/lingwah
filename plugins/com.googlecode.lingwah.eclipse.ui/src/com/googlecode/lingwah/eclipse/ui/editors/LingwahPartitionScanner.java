package com.googlecode.lingwah.eclipse.ui.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.*;

import com.googlecode.lingwah.Grammar;

public class LingwahPartitionScanner 
implements IPartitionTokenScanner 
{

	public LingwahPartitionScanner(Grammar _grammar) {

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

	public String[] getLegalContentTypes() {
		// TODO Auto-generated method stub
		return null;
	}
}
